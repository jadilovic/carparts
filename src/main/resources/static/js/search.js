const menu = [
  {
    id: 0,
    title: "Ulja / masti / automobilske kemikalije",
    categories: [
    		{
    		id: 0,
    		title: "Ulja",
    		categories: [{
        				id: 1,
        				title: "Motorno ulje",
        			},
        			{
        				id: 2,
        				title: "Tehnicka maziva",
        			},
        			{
        				id: 3,
        				title: "Ulje za prijenos",
        			},
        		],
    		},
    		{
    		id: 0,
    		title: "Masti",
    		categories: [{
    					id: 4,
    					title: "Masti za lezajeve",
    				},
    				{
    					id: 5,
    					title: "Masti za podmazivanje centralnog sustava",
    				},
    				{
    					id: 6,
    					title: "Biorazgradiva mast",
    				}
    			],
    		},
    		{
    		id: 0,
    		title: "Rashladna tekucina protiv smrzavanja",
    		categories: [{
    					id: 7,
    					title: "Tekucina za hladjenje",
    				},
    				{
    					id: 8,
    					title: "Uredjaj za hladjenje",
    				},
    			],
    		},
    	],
    }, 
    {
      id: 0,
      title: "Pogon",
      categories: [
      		{
      		id: 0,
      		title: "Elektricno kretanje",
      		categories: [{
      					id: 9,
      					title: "Elektricno kretanje", 
      				},
      				{
      					id: 10,
      					title: "Elementi elektricnog kretanja",
      				},
      			],
      		},
      		{
      		id: 0,
      		title: "Kvacilo",
      		categories: [{
      					id: 11,
      					title: "Otpusna piksna kvacila",
      				},
      				{
      					id: 12,
      					title: "Lezaj spojnicke osovine",
      				},
      				{
      					id: 13,
      					title: "Nosiva ploca lamele kvacila",
      				},
      			],
      		},
      		{
      		id: 0,
      		title: "Upravljanje kvacilom",
      		categories: [{
      					id: 14,
      					title: "Sajla kvacila",
      				},
      				{
      					id: 15,
      					title: "Crijeva / cijevi kvacila",
      				},
      				{
      					id: 16,
      					title: "Vilica kvacila",
      				},
      			],
      		},
      	],
    }, 
];

let tempMenu = menu;
let prevMenu = [];

const sectionCenter = document.querySelector(".section-center");

const btnsContainer = document.querySelector(".btn-container");

// load page
window.addEventListener("DOMContentLoaded", function(){
	displayMenuItems(menu);
	
//	const categories = menu.map(function(item){
//		return item.category;
//	})
	displayMenuButtons(menu);
});

// display
function displayMenuItems(menuItems){
	// console.log("sake and bake");
	/*
	let displayMenu = menuItems.map(function(item){
		// console.log(menuItem);

		return `<article class="menu-item">
				<img alt=${item.title} src=${item.img} class="photo">
    			<div class="item-info">
    				<header>
    				   	<h4>${item.title}</h4>
    					<h4 class="price">$${item.price}</h4>
    				</header>
					<p class="item-text">
					${item.desc}
					</p>
    			</div>
    		</article>`;
	});
	
	displayMenu = displayMenu.join("");
	
	// console.log(displayMenu);
	
	sectionCenter.innerHTML = displayMenu;
	
//	console.log(menu);
*/
}

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
		["Pocetno"],
	);
	
	categories.push("Prethodno");
	console.log(ids);
	let categoryBtns;
	
	if(ids.length === 0){
		categoryBtns = categories.map(function(category){
			return `<button class="filter-btn" type="button" data-id='${category}'>${category}</button>`
		}).join("");
		//console.log(categoryBtns);
	} else {
		let count = -1;
		categoryBtns = categories.map(function(category){
			count++;
			return `<button class="filter-btn" type="button" data-id='${category}'>${category} - ${ids[count]}</button>`
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

		if(titleName === "Pocetno"){
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