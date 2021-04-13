from container import Container as C


class ContainerSuit:
    def __init__(self):
        self.suit = []

    def getContainersNum(self):
        return len(self.suit)

    def addNewAndPut(self, cargo):
        newContainer = C(cargo)
        if newContainer.capacity >= cargo.size:
            self.suit.append(newContainer)

    def getTotalContainersSize(self):
        totalSize = 0
        for container in self.suit:
            totalSize += container.getCargosTotalSize()
        return totalSize

    def getLastContainer(self):
        if self.getContainersNum():
            return self.suit[-1]
        return False

    def putCargoAtLast(self, cargo):
        self.getLastContainer().addCargo(cargo)

    def addNewEmpty(self):
        container = C()
        self.suit.append(container)

    def getFirstSuitable(self, cargo):
        iter = 0
        for container in self.suit:
            iter += 1
            if container.FreeSpace() >= cargo.size:
                return {'container': container, 'iter': iter}
        return False

    def getMostEmpty(self):
        mostEmpty = self.suit[0]
        iter = 0
        for container in self.suit:
            iter += 1
            if container.FreeSpace() > mostEmpty.FreeSpace():
                mostEmpty = container
        return {'container': mostEmpty, 'iter': iter}

    def getMostFullAndSuitable(self, cargo):
        answer = False
        tempContainer = self.suit[0]
        iter = 0
        for container in self.suit:
            containerSpace = container.FreeSpace()
            iter += 2
            if tempContainer.FreeSpace() > containerSpace >= cargo.size:
                tempContainer = container
                answer = True
        if answer:
            return {'container': tempContainer, 'iter': iter}
        else:
            return answer
