import numpy as np

ex = np.array([10, 50, 3])
print(type(ex))
print(ex.shape)
print(ex[0], ex[1], ex[2])
ex[2] = 70
print(ex)

b = np.array([[12,23,32],[41,54,62]])
print(b.shape)
print(b[0, 0], b[0, 1], b[1, 0])   