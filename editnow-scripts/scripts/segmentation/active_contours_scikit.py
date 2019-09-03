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
imageName = 'test_astronaut_2.jpg'
processedImageName = 'test_astronaut_output.png'


# load image
image = io.imread(path + imageName)

#
s = np.linspace(0, 2*np.pi, 400)
r = 100 + 100*np.sin(s)
c = 220 + 100*np.cos(s)
init = np.array([r, c]).T

snake = active_contour(gaussian(image, 3), init, alpha=0.015, beta=10, gamma=0.001)

for i in range(len(snake) - 1):
    image[snake[i], snake[i+1]] = 1
# ### work

# ### end work

# save image
io.imsave(path + processedImageName, image)