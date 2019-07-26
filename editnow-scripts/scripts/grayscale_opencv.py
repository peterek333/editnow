#!/usr/bin/env python

from sys import argv
import cv2

path = argv[1]
imageName = argv[2]
processedImageName = argv[3]

print(path + imageName)

image = cv2.imread(path + imageName)
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

cv2.imwrite(path + processedImageName, gray)