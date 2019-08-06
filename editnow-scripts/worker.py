from argparse import ArgumentParser
import json, os
import subprocess
import pika
from pika.exceptions import AMQPConnectionError
# import MySQLdb
import base64

ACTION_QUEUE_NAME = 'action'
COMPLETED_ACTION_QUEUE_NAME = 'completedAction'
SCRIPTS_FOLDER_PATH = './scripts/'
IMAGES_FOLDER_PATH = './images/'

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
    parser.add_argument('-l', '--local', default=False,
                        help='Configure local environment if true - else configure for cloud (PCF)')
    args = parser.parse_args()


def prepareEnvironment():
    # global amqp_uri
    initRabbitMQConnection()
    # initDatabaseConnection()
    createDirectories()


def initRabbitMQConnection():
    global rabbitMQConnection, args

    amqp_uri = pika.ConnectionParameters('localhost')
    if not args.local:
        amqp_uri = pika.URLParameters(json.loads(os.environ['VCAP_SERVICES'])['cloudamqp'][0]['credentials']['uri'])

    try:
        rabbitMQConnection = pika.BlockingConnection(amqp_uri)
    except AMQPConnectionError:
        print(' RabbitMQ connection problem')


def createDirectories():
    if not os.path.exists(os.path.dirname(IMAGES_FOLDER_PATH)):
        os.makedirs(os.path.dirname(IMAGES_FOLDER_PATH))
        print('Path: \'' + IMAGES_FOLDER_PATH + '\' created')


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
        inputImageName = bodyDict['inputImageName']
        saveImageFromBase64(inputImageName, bodyDict['imageBase64'])
        outputImageName = 'output_' + inputImageName
        runScriptAndWaitForFinish(actionScriptName, [IMAGES_FOLDER_PATH, inputImageName, outputImageName])
        print('Finished script')
        # changeActionStatusToComplete(bodyDict['id'])
        sendCompletedAction(bodyDict['actionId'], outputImageName)


def saveImageFromBase64(inputImageName, imageBase64):
    imagePath = IMAGES_FOLDER_PATH + inputImageName
    with open(imagePath, 'wb') as image:
        image.write(base64.b64decode(imageBase64))

# def changeActionStatusToComplete(actionId):
#     global dbConnection
#     cursor = dbConnection.cursor()
#     cursor.execute("UPDATE `actions` SET `status` = 'COMPLETED' WHERE id = %s", (actionId, ))
#     dbConnection.commit()


def sendCompletedAction(actionId, outputImageName):
    completedActionChannel = rabbitMQConnection.channel()
    completedActionChannel.queue_declare(queue=COMPLETED_ACTION_QUEUE_NAME, durable=True)
    resultData = {'actionId': actionId}
    with open(IMAGES_FOLDER_PATH + outputImageName, 'rb') as image:
        resultData['imageBase64'] = base64.b64encode(image.read()).decode('ascii')
    completedActionChannel.basic_publish(exchange='', routing_key=COMPLETED_ACTION_QUEUE_NAME, body=json.dumps(resultData))
    completedActionChannel.close()



if __name__ == '__main__':
    parseArguments()
    prepareEnvironment()

    actionChannel = rabbitMQConnection.channel()
    actionChannel.queue_declare(queue=ACTION_QUEUE_NAME, durable=True)
    actionChannel.basic_consume(queue=ACTION_QUEUE_NAME, auto_ack=True, on_message_callback=handleAction)

    print('\n\nWaiting for messages on action queue..')
    actionChannel.start_consuming()

