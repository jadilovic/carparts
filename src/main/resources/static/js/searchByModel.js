

let tempMenu = menu;
let prevMenu = [];

const btnsContainer = document.querySelector(".cardy");
const categorySelection = document.querySelector(".groupName");

// load page
window.addEventListener("DOMContentLoaded", function(){
	displayMenuButtons(menu);
});

//button display
function displayMenuButtons(menuItems){
		const ids = [];
		const categories = menuItems.reduce(function(values, item){
			if(item.id > 0){
				ids.push(item.id);
			}
				console.log(item.title);
				values.push(item.title);
				return values;
			}, 
			["Početno"],
		);
		
		categories.push("Prethodno");
		if(categories.lenght < 3){
			btnsContainer.innerHTML = categoryBtns;
		}
		console.log(ids);
		let categoryBtns;
		
		if(ids.length === 0){
			categoryBtns = categories.map(function(category){
				if(category === "Početno" || category === "Prethodno"){
					return `<div class="card-body">
					<button type="button" class="btn btn-success btn-block filter-btn" data-id='${category}'>${category}</button>
					</div>`
				} else {
					return `<div class="card-body">
					<button type="button" class="btn btn-primary btn-block filter-btn" data-id='${category}'>${category}</button>
					</div>`
				}
			}).join("");
			//console.log(categoryBtns);
		} else {
			let count = -2;
			categoryBtns = categories.map(function(category){
				count++;
				if(count < 0 || count > ids.length - 1){
					return `<div class="card-body">
					<button type="button" class="btn btn-success btn-block filter-btn" data-id='${category}'>${category}</button>
					</div>`
				} else {
					return `<option value="${ids[count]}">${category}</option>`
				}
			}).join("");
			//console.log(categoryBtns);
		}
		
		console.log("prije " + categoryBtns);
		if(ids.length > 0){
			categorySelection.innerHTML = categoryBtns;
			btnsContainer.innerHTML = "";
		} else {
			
			btnsContainer.innerHTML = categoryBtns;
			
			const filterBtns = btnsContainer.querySelectorAll(".filter-btn");


			// buttons
			filterBtns.forEach(function(btn){
				btn.addEventListener("click", function(e){

				const titleName = e.currentTarget.dataset.id;
				
					// console.log(titleName);
					let menuCategories = [];
					tempMenu.forEach(function(menuObject){
						if(menuObject.title === titleName){
							menuCategories = menuObject.categories;
							prevMenu = tempMenu;
							tempMenu = menuObject.categories;
						}
					});

					if(titleName === "Početno"){
						displayMenuButtons(menu);
						tempMenu = menu;
					} else if(titleName === "Prethodno"){
						displayMenuButtons(prevMenu);
						tempMenu = prevMenu;
					} else {
						displayMenuButtons(menuCategories);
					}
				});
			});		
		}
}