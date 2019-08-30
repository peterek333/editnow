import numpy as np

from skimage.segmentation import random_walker
from skimage.data import binary_blobs
from skimage.exposure import rescale_intensity
import skimage
from skimage import io

path = '../'
imageName = 'test_astronaut_output.png'
processedImageName = 'test_random_walker.png'

data = io.imread(path + imageName)

# Generate noisy synthetic data
# data = skimage.img_as_float(binary_blobs(length=128, seed=1))
# sigma = 0.35
# data += np.random.normal(loc=0, scale=sigma, size=data.shape)
# data = rescale_intensity(data, in_range=(-sigma, 1 + sigma),
#                          out_range=(-1, 1))

# The range of the binary image spans over (-1, 1).
# We choose the hottest and the coldest pixels as markers.
# Na czym polegaja markery?
markers = np.zeros(data.shape, dtype=np.uint)
markers[data < -0.95] = 1
markers[data > 0.95] = 2

# Run random walker algorithm
labels = random_walker(data, markers, beta=10, mode='bf')

io.imsave(path + processedImageName, labels)
io.imsave(path + "test_1.png", data)
io.imsave(path + "test_2.png", markers)