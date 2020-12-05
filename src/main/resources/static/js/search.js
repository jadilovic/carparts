
let menu;
let tempMenu;

function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function() {
        if (rawFile.readyState === 4 && rawFile.status == "200") {
            callback(rawFile.responseText);
        }
    }
    rawFile.send(null);
}

readTextFile("/file/groups.json", function(text){
	menu = JSON.parse(text);
	tempMenu = menu;
	displayMenuButtons(menu);
});

window.addEventListener("DOMContentLoaded", function(){
	 console.log(menu);
});

let prevMenu = [];

const btnsContainer = document.querySelector(".cardy");

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
				return `<div class="card-body">
    			<button onclick="window.location='/home/listproducts/ + ${ids[count]}';" class="btn btn-primary btn-block">${category}</button>
    			</div>`
			}
		}).join("");
		//console.log(categoryBtns);
	}

	
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