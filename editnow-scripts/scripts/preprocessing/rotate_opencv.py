#!/usr/bin/env python

from sys import argv
import cv2

path = './'
imageName = 'test_j_color.png'
processedImageName = 'test_j_out.png'
angle = -90

print(path + imageName)

# load image
image = cv2.imread(path + imageName)

# ### work
rows, cols = image.shape[:2]
center = (cols / 2, rows / 2)
M = cv2.getRotationMatrix2D(center, angle, 1)
rotated = cv2.warpAffine(image, M, (cols, rows))
# ### end work

# save image
cv2.imwrite(path + processedImageName, rotated)