class ContainerSize:
    def __init__(self, size):
        if size >= 0:
            self.size = size
        else:
            self.size = not size
