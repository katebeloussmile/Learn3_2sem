class Container:
    def __init__(self, cargoWeight=None, capacity=100):
        self.capacity = capacity
        self.cargos = []
        if cargoWeight:
            self.cargos.append(cargoWeight)

    def FreeSpace(self):
        return self.capacity - self.getCargoWeight()

    def isEmpty(self):
        return len(self.cargos) != 0

    def getCargoWeight(self):
        totalSize = 0
        for cargo in self.cargos:
            totalSize += cargo.size
        return totalSize

    def addCargo(self, cargo):
        if self.FreeSpace() >= cargo.size:
            self.cargos.append(cargo)
