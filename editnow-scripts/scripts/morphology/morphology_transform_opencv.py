#!/usr/bin/env python

from sys import argv
import cv2
import numpy as np


def morph_types(morphType):
    return {
       0: cv2.MORPH_OPEN,
       1: cv2.MORPH_CLOSE,
       2: cv2.MORPH_GRADIENT,
       3: cv2.MORPH_TOPHAT,
       4: cv2.MORPH_BLACKHAT,
       5: cv2.MORPH_DILATE,
       6: cv2.MORPH_ERODE
    }.get(morphType, None)

# parse input
# argv[1] - images path like "./images/"
# argv[2] - input image name (before processed)
# argv[3} - output image name (after processed)
path = '../'
imageName = 'test_j_color.png'
processedImageName = 'test_j_out.png'
kernelRows = 2
kernelCols = 5
morphTypeIndex = 0

# calculate before
morphType = morph_types(morphTypeIndex)

print(morphType)
print(path + imageName)
for i in range(0, 7):
    print(morph_types(i))

# load image
image = cv2.imread(path + imageName)

# ### work
kernel = np.ones((kernelRows, kernelCols), np.uint8)
print(kernel)
morphology_transform = cv2.morphologyEx(image, morphType, kernel)  # iterations = X
# ### end work

# save image
cv2.imwrite(path + processedImageName, morphology_transform)