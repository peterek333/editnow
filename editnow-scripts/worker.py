from argparse import ArgumentParser
import json, os
import subprocess
import pika
from pika.exceptions import AMQPConnectionError
import MySQLdb

ACTION_QUEUE_NAME = 'action'
COMPLETED_ACTION_QUEUE_NAME = 'completedAction'
SCRIPTS_FOLDER_PATH = './scripts/'
IMAGES_FOLDER_PATH = '../images/'

args = []
# amqp_uri = 'localhost'
rabbitMQConnection = None
dbConnection = None

# TODO change related function/action - python scripts to database
def actions(name):
    acs = {
       'grayscale': 'grayscale_opencv.py'
    }
    return acs.get(name, None)

def parseArguments():
    global args
    parser = ArgumentParser()
    parser.add_argument('-l', '--local', default=True,
                        help='Configure local environment if true - else configure for cloud (PCF)')
    args = parser.parse_args()


def prepareEnvironment():
    # global amqp_uri
    initRabbitMQConnection()
    initDatabaseConnection()


def initRabbitMQConnection():
    global rabbitMQConnection
    amqp_uri = pika.ConnectionParameters('localhost') if args.local \
        else json.loads(os.environ['VCAP_SERVICES'])['cloudamqp'][0]['credentials']['uri']
    try:
        print(amqp_uri)
        rabbitMQConnection = pika.BlockingConnection(amqp_uri)
    except AMQPConnectionError:
        print(' RabbitMQ connection problem')


def initDatabaseConnection():
    global dbConnection
    dbConnection = MySQLdb.connect(host='127.0.0.1', port=3306, user='admin', passwd='admin123', db='editnow')


def runScriptAndWaitForFinish(actionScriptName, args):
    scriptPath = SCRIPTS_FOLDER_PATH + actionScriptName
    scriptProcess = subprocess.Popen([scriptPath] + args, shell=False)
    scriptProcessWorking = scriptProcess.poll() is None
    while scriptProcessWorking:
        scriptProcessWorking = scriptProcess.poll() is None


def handleAction(ch, method, properties, body):
    bodyDict = json.loads(body)
    actionName = bodyDict['actionName']
    print('Handle action')
    actionScriptName = actions(actionName)
    if actionScriptName is not None:
        # TODO handle pass multiple arguments
        inputImageName = bodyDict['inputImage']['name']
        outputImageName = bodyDict['outputImage']['name']
        runScriptAndWaitForFinish(actionScriptName, [IMAGES_FOLDER_PATH, inputImageName, outputImageName])
        print('Finished script')
        changeActionStatusToComplete(bodyDict['id'])
        sendCompletedAction(bodyDict['id'])


def changeActionStatusToComplete(actionId):
    global dbConnection
    cursor = dbConnection.cursor()
    cursor.execute("UPDATE `actions` SET `status` = 'COMPLETED' WHERE id = %s", (actionId, ))
    dbConnection.commit()


def sendCompletedAction(actionId):
    completedActionChannel = rabbitMQConnection.channel()
    completedActionChannel.queue_declare(queue=COMPLETED_ACTION_QUEUE_NAME, durable=True)
    completedActionChannel.basic_publish(exchange='', routing_key=COMPLETED_ACTION_QUEUE_NAME, body=json.dumps({"id": actionId}))
    completedActionChannel.close()



if __name__ == '__main__':
    parseArguments()
    prepareEnvironment()

    actionChannel = rabbitMQConnection.channel()
    actionChannel.queue_declare(queue=ACTION_QUEUE_NAME, durable=True)
    actionChannel.basic_consume(queue=ACTION_QUEUE_NAME, auto_ack=True, on_message_callback=handleAction)

    print('\n\nWaiting for messages on action queue..')
    actionChannel.start_consuming()

