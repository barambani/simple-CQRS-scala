package SimpleCqrsScala.CommandSide

import java.util.UUID

object InventoryItem {

	private val initialId = new UUID(0, 0)
	private val initialState = new InventoryItem()

	def apply(history: List[Event]): InventoryItem =
		(history foldRight initialState) ((e, s) => s getStateWhen e)
}

class InventoryItem private (
	val id: UUID, 
	val name: String, 
	val isActivated: Boolean,
	val itemsCount: Int = 0,
	val version: Long = 0) {
	
	private def this() = this(InventoryItem.initialId, "", false)

	private def hasTheCorrectId(event: Identity): Boolean = 
		id == new UUID(0, 0) || event.id == id

	private def nextVersion: Long = version + 1
	private def isInSequence(event: Sequenced): Boolean = event.sequence == nextVersion

	private def countAfterCheckIn(toCheckin: Int): Int = itemsCount + toCheckin
	private def countAfterRemoval(toRemove: Int): Int = itemsCount - toRemove

	def getStateWhen(event: Event): InventoryItem = 

		if(!isInSequence(event)) this // TODO: Error in this case
		else if(!hasTheCorrectId(event)) this // TODO: Error in this case
		else 
			event match {
				case InventoryItemCreated(newId, newName, sequence) => 
					new InventoryItem(newId, newName, true, version = sequence)
				
				case InventoryItemDeactivated(toDeactivateId, sequence) => 
					new InventoryItem(id, name, false, version = sequence)

				case InventoryItemRenamed(toRenameId, newName, sequence) => 
					new InventoryItem(id, newName, isActivated, itemsCount, sequence)

				case ItemsCheckedInToInventory(toCheckinid, count, sequence) => 
					new InventoryItem(id, name, isActivated, countAfterCheckIn(count), sequence)
				
				case ItemsRemovedFromInventory(toRemoveId, count, sequence) => 
					new InventoryItem(id, name, isActivated, countAfterRemoval(count), sequence)
				
				case _ => this
			}

	//	Behavior
	def deactivateInventoryItem: List[Event] =
		InventoryItemDeactivated(id, nextVersion) asHistory

	def renameInventoryItem(newName: String): List[Event] =
		//if(newName,ieEmpty) ERROR
		InventoryItemRenamed(id, newName, nextVersion) asHistory

	def checkInItemsToInventory(count: Int): List[Event] =
		ItemsCheckedInToInventory(id, count, nextVersion) asHistory

	def removeItemsFromInventory(count: Int): List[Event] =
		//if(itemsCount - count < 0) ERROR
		ItemsRemovedFromInventory(id, count, nextVersion) asHistory
}
