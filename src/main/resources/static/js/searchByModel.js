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
        		],
    		},
			{
			id: 0,
			title: "Ulje za prijenos",
			categories: [{
						id: 51,
						title: "Ulje servo upravljača",
					}, 
					{
						id: 221,
						title: "Mineralno ulje za mjenjač",
					}, 
					{
						id: 231,
						title: "LHM tekućina za hidraulične sisteme",
					},
				],
			},
    		{
    		id: 0,
    		title: "Masti",
    		categories: [{
    					id: 61,
    					title: "Masti za ležajeve",
    				},
    				{
    					id: 71,
    					title: "Masti za podmazivanje centralnog sustava",
    				},
    				{
    					id: 81,
    					title: "Biorazgradiva mast",
    				},
    				{
    					id: 241,
    					title: "Specijalna mast",
    				},
    				{
    					id: 251,
    					title: "Univerzalna mast",
    				},
    				{
    					id: 261,
    					title: "Mast podvozja",
    				},
    				{
    					id: 271,
    					title: "Mast homokinetičkog zgloba",
    				},
    			],
    		},	
			{
			id: 0,
			title: "Tehnička maziva",
			categories: [{
						id: 41,
						title: "Biorazgradivo ulje",
					},
					{
						id: 191,
						title: "Ulje za turbine",
					},
					{
						id: 201,
						title: "Ulje za hidrauliku",
					},
					{
						id: 211,
						title: "Ulja industrijske namjene / ostala",
					},
				],
			},    		
    		{
    		id: 0,
    		title: "Rashladna tekućina protiv smrzavanja",
    		categories: [{
    					id: 91,
    					title: "Tekućina za hladnjak motora (tip G12++)",
    				},
    				{
    					id: 101,
    					title: "Tekućina za hladnjak motora (tip G11)",
    				},
    				{
    					id: 281,
    					title: "Tekućina za hladnjak motora (tip G12+)",
    				},
    				{
    					id: 291,
    					title: "Rashladna tekućina (tip G13)",
    				},
    				{
    					id: 301,
    					title: "Tekućina za hladnjak motora (tip G12)",
    				},
    				{
    					id: 311,
    					title: "Rashladna tekućina (tip ECO)",
    				},
    				{
    					id: 321,
    					title: "Koncentrat za hlađenje (tip G11)",
    				},
    				{
    					id: 331,
    					title: "Koncentrat za hlađenje (tip G12++)",
    				},
    				{
    					id: 341,
    					title: "Koncentrat za hlađenje (tip G12+)",
    				},
    				{
    					id: 351,
    					title: "Koncentrat za hlađenje (tip G12)",
    				},
    				{
    					id: 361,
    					title: "Koncentrat za hlađenje (tip G13)",
    				},
    			],
    		},
    		{
        	id: 0,
        	title: "Kočione tekućine",
        	categories: [{
        				id: 371,
        				title: "Kočiona tekućina DOT4",
        			},
        			{
        				id: 381,
        				title: "DOT4 PLUS kočiona tekućina",
        			},
        			{
        				id: 391,
        				title: "R-3 kočiona tekućina",
        			},
        			{
        				id: 401,
        				title: "Kočiona tekućina",
        			},
        			{
        				id: 411,
        				title: "DOT3 kočiona tekućina",
        			},
        			{
        				id: 421,
        				title: "DOT5.1 kočiona tekućina",
        			},
        		],
        	},
    		{
            id: 0,
            title: "Kemikalije za automobile",
            categories: [{
            			id: 431,
            			title: "Odleđivači",
            		},
            		{
            			id: 441,
            			title: "Ljepilo za fiksiranje stakla",
            		},
            		{
            			id: 451,
            			title: "Ljepila",
            		},
            		{
            			id: 461,
            			title: "Čišćenje/Održavanje/Pranje",
            		},
            		{
            			id: 471,
            			title: "Kemikalije za popravak i zaštitu",
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
      		title: "Električno kretanje",
      		categories: [{
      					id: 111,
      					title: "Električno kretanje", 
      				},
      				{
      					id: 121,
      					title: "Elementi električnog kretanja",
      				},
      			],
      		},
      		{
      		id: 0,
      		title: "Kvačilo",
      		categories: [{
      					id: 131,
      					title: "Otpusna piksna kvačila",
      				},
      				{
      					id: 141,
      					title: "Ležaj spojničke osovine",
      				},
      				{
      					id: 151,
      					title: "Nosiva ploča lamele kvačila",
      				},
      				{
      					id: 481,
      					title: "Potisna šipka kvačila",
      				},
      				{
      					id: 491,
      					title: "Sekundarni zamašnjak",
      				},
      				{
      					id: 501,
      					title: "Zamašnjak",
      				},
      				{
      					id: 511,
      					title: "Hidraulični ležaj",
      				},
      				{
      					id: 521,
      					title: "Set samopodešavajućih kvačila",
      				},
      				{
      					id: 531,
      					title: "Potisni ležaj kvačila",
      				},
      				{
      					id: 541,
      					title: "Set kvačila sa zamašnjakom",
      				},
      				{
      					id: 551,
      					title: "Pera potisne ploče",
      				},
      				{
      					id: 561,
      					title: "Set samopodesivog kvačila sa zamašnjakom",
      				},
      				{
      					id: 571,
      					title: "Ležaj vilice kvačila",
      				},
      				{
      					id: 581,
      					title: "Potisna ploča",
      				},
      				{
      					id: 591,
      					title: "Lamela kvačila",
      				},
      			],
      		},
      		{
      		id: 0,
      		title: "Upravljanje kvačilom",
      		categories: [{
      					id: 161,
      					title: "Sajla kvačila",
      				},
      				{
      					id: 171,
      					title: "Crijeva / cijevi kvačila",
      				},
      				{
      					id: 181,
      					title: "Vilica kvačila",
      				},
      				{
      					id: 601,
      					title: "Osovina vilice mjenjača",
      				},
      				{
      					id: 611,
      					title: "Sekundarni cilindar kvačila",
      				},
      				{
      					id: 621,
      					title: "Vilica kvačila",
      				},
      				{
      					id: 631,
      					title: "Glavni cilindar kvačila",
      				},
      				{
      					id: 641,
      					title: "Sekundarni cilindar kvačila",
      				},
      				{
      					id: 651,
      					title: "Samoregulator kvačila",
      				},
      				{
      					id: 661,
      					title: "Nosač sajle kvačila",
      				},
      				{
      					id: 671,
      					title: "Glavni cilindar kvačila + set za reparaturu",
      				},
      			],
      		},
      		{
          	id: 0,
          	title: "Mjenjač",
          	categories: [{
          				id: 681,
          				title: "Spojnička osovina mjenjača",
          			},
          			{
          				id: 691,
          				title: "Kučište mjenjača",
          			},
        			{
          				id: 701,
          				title: "Mjenjanje brzina",
          			},
         			{
          				id: 711,
          				title: "Brtva mjenjača",
          			},
         			{
          				id: 721,
          				title: "Sinhron",
          			},
         			{
          				id: 731,
          				title: "Planetarni zupčanik, mjenjač",
          			},
          			{
          				id: 741,
         				title: "Ručica mjenjača",
          			},
          			{
          				id: 751,
          				title: "Upravljanje automatiziranog mjenjača",
         			},
          			{
          				id: 761,
          				title: "Filter automatskog mjenjača",
         			},
          			{
          				id: 771,
          				title: "Zupčanici mjenjača",
         			},
          			{
          				id: 781,
          				title: "Viseći nosač mjenjača",
         			},
         			{
          				id: 791,
          				title: "Ručna kontrola mjenjača",
          			},
          			{
          				id: 801,
         				title: "Gearbox element",
          			},
          			{
          				id: 811,
          				title: "Automatic transmission",
         			},
          			{
          				id: 821,
          				title: "Gearbox cooling",
         			},
          			{
          				id: 831,
          				title: "Gearbox bearings",
         			},
          			{
          				id: 841,
          				title: "Gearbox assembly",
         			},
          		],
          	},
      	],
    }, 
];

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
					return `<option value="${category}">${category}</option>`
				}
			}).join("");
			//console.log(categoryBtns);
		}
		
		console.log("prije " + categoryBtns);
		if(ids.length > 0){
			categorySelection.innerHTML = categoryBtns;
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