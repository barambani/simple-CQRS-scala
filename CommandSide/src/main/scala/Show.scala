package SimpleCqrsScala.CommandSide

import SimpleCqrsScala.CommandSide.Domain.Events._
import SimpleCqrsScala.CommandSide.Domain.Events.Event._
import SimpleCqrsScala.CommandSide.Domain.Errors._

trait Show[A] {
	def stringFor(a: A): String
}

object Show {

	def apply[A](implicit instance: Show[A]): Show[A] = instance

	// Events
	implicit object InventoryItemCreatedShow extends Show[InventoryItemCreated] {
		def stringFor(a: InventoryItemCreated): String = s"seq: ${a.sequence} - Item ${a.name} created (id: ${a.id})"
	}
	implicit object InventoryItemDeactivatedShow extends Show[InventoryItemDeactivated] {
		def stringFor(a: InventoryItemDeactivated): String = s"seq: ${a.sequence} - Item deactivated (id: ${a.id})"
	}
	implicit object InventoryItemRenamedShow extends Show[InventoryItemRenamed] {
		def stringFor(a: InventoryItemRenamed): String = s"seq: ${a.sequence} - Item renamed to ${a.newName} (id: ${a.id})"
	}
	implicit object ItemsCheckedInToInventoryShow extends Show[ItemsCheckedInToInventory] {
		def stringFor(a: ItemsCheckedInToInventory): String = s"seq: ${a.sequence} - Check-in of ${a.count} item/s (id: ${a.id})"
	}
	implicit object ItemsRemovedFromInventoryShow extends Show[ItemsRemovedFromInventory] {
		def stringFor(a: ItemsRemovedFromInventory): String = s"seq: ${a.sequence} - Removed ${a.count} item/s (id: ${a.id})"
	}

	//	Errors
	implicit object InventoryItemNameNotValidShow extends Show[InventoryItemNameNotValid] {
		def stringFor(a: InventoryItemNameNotValid): String = s"the name '${a.requestedName}' proposed for the item ${a.name} (id: ${a.itemId}) is not valid"
	}
	implicit object NotEnoughItemsInStockShow extends Show[NotEnoughItemsInStock] {
		def stringFor(a: NotEnoughItemsInStock): String = s"the inventory doesn't have ${a.requestedCount} items of ${a.name} (id: ${a.itemId})"
	}
	implicit object OrderClosedShow extends Show[OrderClosed] {
		def stringFor(a: OrderClosed): String = s"the order ${a.description} (id: ${a.orderId}) cannot be changed as it has been closed"
	}
	implicit object NotEnoughItemsInTheOrderShow extends Show[NotEnoughItemsInTheOrder] {
		def stringFor(a: NotEnoughItemsInTheOrder): String =
			s"impossible to remove ${a.requestedCount} of items (of id: ${a.itemId}) from the order ${a.description} (id: ${a.orderId})"
	}
	implicit object ShippingAddressNotValidShow extends Show[ShippingAddressNotValid] {
		def stringFor(a: ShippingAddressNotValid): String =
			s"the shipping address '${a.address}' proposed for the order ${a.description} (id: ${a.orderId}) is not valid"
	}
	implicit object OrderAlreadyPayedShow extends Show[OrderAlreadyPayed] {
		def stringFor(a: OrderAlreadyPayed): String = s"the order ${a.description} (id: ${a.orderId}) is already payed and can't be payed again"
	}
	implicit object OrderNotCompleteShow extends Show[OrderPaymentIsNotValid] {
		def stringFor(a: OrderPaymentIsNotValid): String = 
			s"the payment for order ${a.description} (id: ${a.orderId}) is not valid"
	}
	implicit object OrderContainsNoItemsShow extends Show[OrderContainsNoItems] {
		def stringFor(a: OrderContainsNoItems): String = 
			s"the order ${a.description} (id: ${a.orderId}) contains no items"
	}
}

