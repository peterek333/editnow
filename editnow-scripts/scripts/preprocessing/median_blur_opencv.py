#!/usr/bin/env python

from sys import argv
import cv2

# parse input
# argv[1] - images path like "./images/"
# argv[2] - input image name (before processed)
# argv[3} - output image name (after processed)
path = './'
imageName = 'test_rgb.jpg'
processedImageName = 'test_rgb_out.jpg'
ksize = 3

print(path + imageName)

# load image
image = cv2.imread(path + imageName)

# ### work
image = cv2.medianBlur(image, ksize)
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)