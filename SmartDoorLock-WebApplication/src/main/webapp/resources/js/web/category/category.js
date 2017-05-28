//이거 안씀.
$(document).ready(function() {
	var adjustment;
	$("#key_list").sortable({

		group : 'list-unstyled',
		pullPlaceholder : true,
		placeholder: '<div class="sortable_cursor"><i class="fa fa-paper-plane"/></div>',
		// animation on drop
		onDrop : function($item, container, _super) {
			var $clonedItem = $('<li/>').css({
				height : 3
			});
			$item.before($clonedItem);
			$clonedItem.animate({
				'height' : $item.height()
			});

			$item.animate($clonedItem.position(), function() {
				$clonedItem.detach();
				_super($item, container);
			});
		},

		// set $item relative to cursor position
		onDragStart : function($item, container, _super) {
			var offset = $item.offset(), pointer = container.rootGroup.pointer;
			adjustment = {
				left : pointer.left - offset.left,
				top : pointer.top - offset.top
			};
			_super($item, container);
		},
		onDrag : function($item, position) {
			$item.css({
				left : position.left - adjustment.left,
				top : position.top - adjustment.top
			});
		}
	});
	var adjustment2;
	$("#category_list").sortable({

		group : 'list-unstyled',
		pullPlaceholder : true,
		placeholder: '<div class="sortable_cursor"><i class="fa fa-paper-plane"/></div>',
		// animation on drop
		onDrop : function($item, container, _super) {
			var $clonedItem = $('<li/>').css({
				height : 3
			});
			$item.before($clonedItem);
			$clonedItem.animate({
				'height' : $item.height()
			});

			$item.animate($clonedItem.position(), function() {
				$clonedItem.detach();
				_super($item, container);
			});
		},

		// set $item relative to cursor position
		onDragStart : function($item, container, _super) {
			var offset = $item.offset(), pointer = container.rootGroup.pointer;
			adjustment2 = {
				left : pointer.left - offset.left,
				top : pointer.top - offset.top
			};
			_super($item, container);
		},
		onDrag : function($item, position) {
			$item.css({
				left : position.left - adjustment2.left,
				top : position.top - adjustment2.top
			});
		}
	});
});

function categoryModalShow(){
	categoryModalInit();
	$("#categoryModalCreate").modal("show");
}
function categoryCreate(){
	
}
function categoryModalInit(){
	$("#category_name").val("");
}
