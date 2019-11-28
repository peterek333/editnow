#!/usr/bin/env python

from sys import argv
import cv2
import numpy as np

# parse input
# argv[1] - images path like "./images/"
# argv[2] - input image name (before processed)
# argv[3} - output image name (after processed)
path = './'
imageName = 'test_j_color.png'
processedImageName = 'test_j_out.png'
kernelMin = 5
kernelMax = 5

print(path + imageName)

# load image
image = cv2.imread(path + imageName)

# ### work
kernel = np.ones((kernelMin, kernelMax), np.uint8)
dilation = cv2.dilate(image, kernel)  # iterations = X
# ### end work

# save image
cv2.imwrite(path + processedImageName, dilation)