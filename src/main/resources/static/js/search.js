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
        				id: 41,
        				title: "Tehnicka maziva",
        			},
        			{
        				id: 51,
        				title: "Ulje za prijenos",
        			},
        		],
    		},
    		{
    		id: 0,
    		title: "Masti",
    		categories: [{
    					id: 61,
    					title: "Masti za lezajeve",
    				},
    				{
    					id: 71,
    					title: "Masti za podmazivanje centralnog sustava",
    				},
    				{
    					id: 81,
    					title: "Biorazgradiva mast",
    				}
    			],
    		},
    		{
    		id: 0,
    		title: "Rashladna tekucina protiv smrzavanja",
    		categories: [{
    					id: 91,
    					title: "Tekucina za hladjenje",
    				},
    				{
    					id: 101,
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
      					id: 111,
      					title: "Elektricno kretanje", 
      				},
      				{
      					id: 121,
      					title: "Elementi elektricnog kretanja",
      				},
      			],
      		},
      		{
      		id: 0,
      		title: "Kvacilo",
      		categories: [{
      					id: 131,
      					title: "Otpusna piksna kvacila",
      				},
      				{
      					id: 141,
      					title: "Lezaj spojnicke osovine",
      				},
      				{
      					id: 151,
      					title: "Nosiva ploca lamele kvacila",
      				},
      			],
      		},
      		{
      		id: 0,
      		title: "Upravljanje kvacilom",
      		categories: [{
      					id: 161,
      					title: "Sajla kvacila",
      				},
      				{
      					id: 171,
      					title: "Crijeva / cijevi kvacila",
      				},
      				{
      					id: 181,
      					title: "Vilica kvacila",
      				},
      			],
      		},
      	],
    }, 
];

let tempMenu = menu;
let prevMenu = [];

const btnsContainer = document.querySelector(".cardy");

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
		["Pocetno"],
	);
	
	categories.push("Prethodno");
	console.log(ids);
	let categoryBtns;
	
	if(ids.length === 0){
		categoryBtns = categories.map(function(category){
			return `<div class="card-body">
			<button type="button" class="btn btn-primary btn-block filter-btn" data-id='${category}'>${category}</button>
			</div>`
		}).join("");
		//console.log(categoryBtns);
	} else {
		let count = -2;
		categoryBtns = categories.map(function(category){
			count++;
			if(count < 0 || count > ids.length - 1){
				return `<div class="card-body">
				<button type="button" class="btn btn-primary btn-block filter-btn" data-id='${category}'>${category}</button>
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