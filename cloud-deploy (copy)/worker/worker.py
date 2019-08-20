import json, os
import pika
import subprocess
import time
from threading import Timer
from pika.exceptions import AMQPConnectionError

def subscribeCallback(ch, method, properties, body):
    print('Consumed ' + body + '\n')
    print('\n\n')

def testDrugi():
    subprocess.Popen("./drugi.py", shell=True)

def testMethod():
    onlyfiles = [f for f in os.listdir("./") if os.path.isfile(os.path.join("./", f))]
    print(onlyfiles)
    os.system("./drugi.py")
    time.sleep(2)
    p = subprocess.Popen("./drugi_sleep.py", shell=False)
    working = p.poll() == None
    while working:
	working = p.poll() == None
    print(p.poll())
    print("\nEnd of work\n")
    #Timer(2, testDrugi).start()
    print("\n\n")
    time.sleep(4)


try:
    testMethod()
    vcap = os.environ['VCAP_SERVICES']
    print(vcap)
    # print(json.loads(vcap))
    # print('\n')
    amqp_uri = json.loads(vcap)['cloudamqp'][0]['credentials']['uri']
    connection = pika.BlockingConnection( pika.URLParameters(amqp_uri) )

    channel = connection.channel()
    # channel.queue_declare(queue='test-queue', durable=True)
    channel.basic_consume(queue='test-queue', auto_ack=True, on_message_callback=subscribeCallback)

    print('\n\nWaiting for messages on test-queue..')
    channel.start_consuming()

except AMQPConnectionError:
    print(' RabbitMQ connection problem')
