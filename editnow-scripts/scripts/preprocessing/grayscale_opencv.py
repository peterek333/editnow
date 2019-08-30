#!/usr/bin/env python

from sys import argv
import cv2

# parse input
# argv[1] - images path like "./images/"
# argv[2] - input image name (before processed)
# argv[3} - output image name (after processed)
path = argv[1]
imageName = argv[2]
processedImageName = argv[3]

print(path + imageName)

# load image
image = cv2.imread(path + imageName)

# ### work
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
# ### end work

# save image
cv2.imwrite(path + processedImageName, gray)