#!/usr/bin/env python

from sys import argv
import cv2
import numpy as np
from skimage.color import rgb2gray
from skimage import data
from skimage.filters import gaussian
from skimage.segmentation import active_contour
from skimage import io
from skimage import img_as_ubyte


# parse input
# argv[1] - images path like "./images/"
# argv[2] - input image name (before processed)
# argv[3} - output image name (after processed)
path = '../'
imageName = 'test_rgb.jpg'
processedImageName = 'test_astronaut.jpg'
reshapeValue = 3
maxIter = 100
epsilon = 1
K = 8

def showImageStatistics(image):
    print(len(image))
    print(image.shape)
    print(image)

# calculate before

# load image
image = cv2.imread(path + imageName)
img = io.imread(path + imageName)
print("OpenCV\n")
showImageStatistics(image)
print("\n\nScikit\n")
showImageStatistics(img)
# ### work
# img = data.astronaut()
img = img[:, :, ::-1]   # convert RGB to BGR
# ### end work

# save image
cv2.imwrite(path + processedImageName, image)
cv2.imwrite(path + "test_rgb_scikit.jpg", img)
io.imsave(path + "test_rgb_scikit_save.jpg", img)