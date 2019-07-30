from argparse import ArgumentParser
import json, os
import subprocess
import pika
from pika.exceptions import AMQPConnectionError

ACTION_QUEUE_NAME = 'action'
FINISHED_ACTION_QUEUE_NAME = 'finishedAction'
SCRIPTS_FOLDER_PATH = './scripts/'
IMAGES_FOLDER_PATH = '../images/'

args = []
# amqp_uri = 'localhost'
rabbitMQConnection = None

# TODO change related function/action - python scripts to database
def actions(name):
    acs = {
       "grayscale": "grayscale_opencv.py"
    }
    return acs.get(name, None)

def parseArguments():
    global args
    parser = ArgumentParser()
    parser.add_argument("-l", "--local", default=True,
                        help="Configure local environment if true - else configure for cloud (PCF)")
    args = parser.parse_args()


def prepareEnvironment():
    # global amqp_uri
    initRabbitMQConnection()


def initRabbitMQConnection():
    global rabbitMQConnection
    amqp_uri = pika.ConnectionParameters('localhost') if args.local \
        else json.loads(os.environ['VCAP_SERVICES'])['cloudamqp'][0]['credentials']['uri']
    try:
        print(amqp_uri)
        rabbitMQConnection = pika.BlockingConnection(amqp_uri)
    except AMQPConnectionError:
        print(' RabbitMQ connection problem')


def runScriptAndWaitForFinish(actionScriptName, args):
    scriptPath = SCRIPTS_FOLDER_PATH + actionScriptName
    scriptProcess = subprocess.Popen([scriptPath] + args, shell=False)
    scriptProcessWorking = scriptProcess.poll() is None
    while scriptProcessWorking:
        scriptProcessWorking = scriptProcess.poll() is None


def handleAction(ch, method, properties, body):
    print("Handle action")
    print(body)
    bodyDict = json.loads(body)
    actionScriptName = actions(bodyDict["actionName"])
    if actionScriptName is not None:
        # TODO handle pass multiple arguments
        processedImageName = bodyDict["actionName"] + "_" + bodyDict["fileName"]
        runScriptAndWaitForFinish(actionScriptName, [IMAGES_FOLDER_PATH, bodyDict["fileName"], processedImageName])
        print("Finished script")
        sendFinishedAction(processedImageName)


def sendFinishedAction(processedImageName):
    finishedActionChannel = rabbitMQConnection.channel()
    finishedActionChannel.basic_publish(exchange='', routing_key=FINISHED_ACTION_QUEUE_NAME, body=json.dumps(processedImageName))
    finishedActionChannel.close()



if __name__ == "__main__":
    parseArguments()
    prepareEnvironment()

    actionChannel = rabbitMQConnection.channel()
    actionChannel.basic_consume(queue=ACTION_QUEUE_NAME, auto_ack=True, on_message_callback=handleAction)

    print('\n\nWaiting for messages on action queue..')
    actionChannel.start_consuming()

