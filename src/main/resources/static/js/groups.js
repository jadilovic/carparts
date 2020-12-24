/**
 * http://usejsdoc.org/
 */
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
      title: "Pogon i punjenje",
      categories: [
      		{
      		id: 0,
      		title: "Pokretanje i punjenje",
      		categories: [{
      					id: 1781,
      					title: "Akumulatori",
      				},
      				{
      					id: 1791,
      					title: "Alternatori",
      				},
      				{
      					id: 1801,
      					title: "Starteri",
      				},
      				{
      					id: 1811,
      					title: "Regulatori napona",
      				},
      				{
      					id: 1821,
      					title: "Releji, senzori i ožičenje",
      				},
      				{
      					id: 1831,
      					title: "Instalacije akumulatora",
      				},
      				{
      					id: 1841,
      					title: "Nosač za montažu",
      				},
      				{
      					id: 1851,
      					title: "Klinasti,kanalni i rebrasti remeni",
      				},
      				{
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
      		{
            id: 0,
            title: "Diferencijal",
            categories: [{
              			id: 851,
              			title: "Osovina diferencijala",
              		},
              		{
              			id: 861,
              			title: "Pogon osovine diferencijala",
              		},
            		{
             			id: 871,
              			title: "Zupčanik diferencijala",
              		},
             		{
              			id: 881,
              			title: "Vijak tanjurastog zupčanika",
              		},
             		{
              			id: 891,
              			title: "Blokada diferencijala (špera)",
              		},
              		{
             			id: 901,
              			title: "Nosači diferencijala",
              		},
              		{
             			id: 911,
             			title: "Semering / brtvilo diferencijala",
              		},
              		{
             			id: 921,
              			title: "Garnitura za reparaturu diferencijala",
             		},
              		{
             			id: 931,
              			title: "Differential element",
             		},
              		{
             			id: 941,
              			title: "Filter ulja za diferencijal",
             		},
              		{
              			id: 951,
             			title: "Čep ispusta",
            		},
             		{
              			id: 961,
              			title: "Oil pressure pump 4x4 drive",
              		},
             	],
            },
      		{
            id: 0,
            title: "Pogonska osovina",
            categories: [{
                 		id: 971,
                  		title: "Semering poluosovine",
                 	},
                  	{
                  		id: 981,
                 		title: "Ležaj pogonske osovine",
                  	},
                	{
                		id: 991,
                  		title: "Zglob poluosovine",
                 	},
                 	{
                  		id: 1001,
                 		title: "Podupirač pogonske osovine",
                  	},
                 	{
                 		id: 1011,
                  		title: "Axle shaft main shaft",
                 	},
                  	{
                 		id: 1021,
                 		title: "Axle sprocket",
                  	},
                  	{
                		id: 1031,
                 		title: "Drive shaft cross-piece",
                 	},
                  	{
                 		id: 1041,
                 		title: "Sigurnosni prsten",
                 	},
                  	{
                		id: 1051,
                  		title: "Zeger poluosovine",
                	},
                  	{
                 		id: 1061,
                  		title: "Jezgra poluosovine",
                 	},
                ],
      		},
      		{
            id: 0,
            title: "Kardan",
            categories: [{
                     	id: 1071,
                      	title: "Elementi za učvršćenje kardana",
                    },
                   	{
                   		id: 1081,
                   		title: "Elastična spojka kardana",
                   	},
                   	{
                   		id: 1091,
                   		title: "Kardan",
                   	},
                   	{
                      	id: 1101,
                     	title: "Među-ležaj, pogonska osovina",
                   	},
                    {
                      	id: 1111,
                   		title: "Zglob kardana",
                   	},
                   	{
                   		id: 1121,
                   		title: "Križ kardana",
                   	},
                  	{
                    	id: 1131,
                     	title: "Indirektna osovina",
                    },
                    {
                     	id: 1141,
                     	title: "Hardi zglob - nosač križa kardana",
                   	},
                    {
                    	id: 1151,
                     	title: "Vodilica rukavca osovine",
                   	},
                    {
                     	id: 1161,
                    	title: "Križ unutrašnjeg homokinetičkog zgloba",
                    },
                    {
                     	id: 1171,
                    	title: "Shaft flange nut",
                    },
                ],
          	},
      		{
          	id: 0,
            title: "Driveshaft joint",
            categories: [{
                       	id: 1181,
                       	title: "Spoj pogonske osovine",
                    },
                    {
                       	id: 1191,
                       	title: "Garnitura za reparaturu zglobova",
                    },
                    {
                       	id: 1201,
                       	title: "Vijak zgloba pogonske osovine",
                    },
                ],
            },        
      		{
            id: 0,
            title: "Manžeta",
            categories: [{
                        id: 1211,
                        title: "Garnitura manžeta homokinetičkog zgloba",
                    },
                    {
                        id: 1221,
                        title: "Šelna manžete",
                    },
                ],
      		},
      		{
            id: 0,
            title: "Getriba",
            categories: [{
                        id: 1231,
                        title: "Nosač kućišta prijenosa",
                    },
                    {
                        id: 1241,
                        title: "Semering radilice",
                    },
                ],
          	},       		
      	],
    },
    {
    id: 0,
    title: "Šasija",
    categories: [
        	{
        	id: 0,
        	title: "Amortizacija vozila",
        	categories: [{
        				id: 0,
        				title: "Dijelovi ovjesa",
        				categories: [{
        						id: 1861,
        						title: "Amortizeri",
        					},
        					        					{
        						id: 1871,
        						title: "Opruge amortizera",
        					},
        					{
        						id: 1881,
        						title: "Lisnate opruge",
        					},  
        					{
        						id: 1891,
        						title: "Zračni ovijes",
        					},
        					{
        						id: 1901,
        						title: "Hidraulički ovijes",
        					},
        					{
        						id: 1911,
        						title: "Vilice",
        					},
        					{
        						id: 1921,
        						title: "Stabilizatori",
        					},
        					{
        						id: 1931,
        						title: "Kugle vilice",
        					},  
        					{
        						id: 1941,
        						title: "Nosači i zaštita amortizera",
        					},  
        					{
        						id: 1951,
        						title: "Rukavci i glavčine",
        					},
        					{
        						id: 1961,
        						title: "Seleni ovjesa i dijelovi za popravak",
        					},  
        					{
        						id: 1971,
        						title: "Most",
        					},  
        					{
        						id: 1981,
        						title: "Gibanj",
        					},
        					{
        						id: 1991,
        						title: "Dijelovi za montažu i komponente",
        					},  
        				], 
        			},
        			{
        				id: 0,
        				title: "Odbojna guma / manžeta amortizera",
        				categories: [{
        						id: 1251,
        						title: "Set opruga",
        					},
        					{
        						id: 1261,
        						title: "Opruga amortizera, stražnja osovina",
        					},
        					{
        						id: 1271,
        						title: "Opruga amortizera, prednja osovina",
        					},
        					{
        						id: 1281,
        						title: "Podložna pločica amortizera",
        					},
        					{
        						id: 1291,
        						title: "Opruga ovjesa naprijed / straga",
        					},  
        					{
        						id: 1771,
        						title: "Tanjur opruga prednji/stražnji",
        					},  
        				], 
        			},      		        			
        		],
        	},
        	{
        	id: 0,
        	title: "Kočnice",
        	categories: [{
        				id: 0,
        				title: "Disk kočnica",
        				categories:[{
        						id: 1301,
        						title: "Vijak vodilice kočionih čeljusti",
        					},
        					{
        						id: 1311,
        						title: "Garnitura za reparaturu kočionih čeljusti",
        					},
        					{
        						id: 1321,
        						title: "Disk pločice",
        					},
        					{
        						id: 1331,
        						title: "Kočioni disk",
        					},
        					{
        						id: 1341,
        						title: "Čeljust kočinog diska",
        					},
        					{
        						id: 1351,
        						title: "Indikator istrošenosti kočionih pločica",
        					},
        					{
        						id: 1361,
        						title: "Garnitura za montažu kočionih pločica",
        					},
        					{
        						id: 1371,
        						title: "Vijak za montažu kočionog diska",
        					},
        					{
        						id: 1381,
        						title: "Vodilica kočionih čeljusti disk kočnica",
        					},
        					{
        						id: 1391,
        						title: "Poklopac klipa kočionih čeljusti",
        					},
        					{
        						id: 1401,
        						title: "Zaštita kočionog diska",
        					},
        					{
        						id: 1411,
        						title: "Klip kočionih čeljusti",
        					},
        					{
        						id: 1421,
        						title: "Kočioni disk s ležajem",
        					},
        					{
        						id: 1431,
        						title: "Disk kočnica, dvodjelni",
        					},
        					{
        						id: 1441,
        						title: "Kabel indikatora istrošenosti",
        					},
        					{
        						id: 1451,
        						title: "Set vijaka za kočioni disk",
        					},
        					{
        						id: 1461,
        						title: "Gumena zaštita vodilice kočionih čeljusti",
        					},
        					{
        						id: 1471,
        						title: "Bolcna mehanizma ručne disk kočnice",
        					},
        				],
        			},
        			{
        				id: 0,
        				title: "Bubanj kočnica",
        				categories:[{
        						id: 1481,
        						title: "Kočione obloge",
        					},
        					{
        						id: 1491,
        						title: "Bubanj kočnica",
        					},
        					{
        						id: 1501,
        						title: "Kočioni cilindar",
        					},
        					{
        						id: 1511,
        						title: "Kočione obloge",
        					},
        					{
        						id: 1521,
        						title: "Garnitura za montažu kočionih obloga",
        					},
        					{
        						id: 1531,
        						title: "Samoregulator koč.obloga bubanj kočnica",
        					},
        					{
        						id: 1541,
        						title: "Osovinica rastezača kočionih obloga",
        					},
        					{
        						id: 1551,
        						title: "Garnitura za reparaturu osovinice rastezača",
        					},
        					{
        						id: 1561,
        						title: "Garnitura za reparaturu poluge rastezača kočionih obloga",
        					},
        					{
        						id: 1571,
        						title: "Elementi za montažu disk pločica",
        					},
        					{
        						id: 1581,
        						title: "Poluga rastezača kočionih obloga",
        					},
        					{
        						id: 1591,
        						title: "Zakovica kočione obloge",
        					},
        					{
        						id: 1601,
        						title: "Kućište kočionog bubnja",
        					},
        					{
        						id: 1611,
        						title: "Indikator istrošenosti kočionih obloga",
        					},
        					{
        						id: 1621,
        						title: "Clutch adjustment quadrant repair kit",
        					},
        					{
        						id: 1631,
        						title: "Brake camshaft sealer",
        					},
        					{
        						id: 1641,
        						title: "Set kočionog bubnja (kočione obloge, cilindri, opruge)",
        					},
        					{
        						id: 1651,
        						title: "Cilindar valjka",
        					},
        					{
        						id: 1661,
        						title: "O-ring kočionih obloga",
        					},
        					{
        						id: 1671,
        						title: "Klizač kočionih obloga",
        					},
        					{
        						id: 1681,
        						title: "Opruga kočionih obloga",
        					},
        					{
        						id: 1691,
        						title: "Bolcna kočionih obloga",
        					},
        					{
        						id: 1701,
        						title: "Piksa kočionih obloga",
        					},
        					{
        						id: 1711,
        						title: "Garnitura za reparaciju kočionih obloga",
        					},
        					{
        						id: 1721,
        						title: "Garnitura za reparaturu kočionog klipa",
        					},
        				],
        			},
        			{
        				id: 0,
        				title: "Sustav pomoći kočenju",
        				categories:[{
        						id: 1731,
        						title: "Servo uređaj kočnica",
        					},
        					{
        						id: 1741,
        						title: "Vakum pumpe",
        					},
        				],
        			},
        			{
        				id: 0,
        				title: "Spremnik kočione tekućine",
        				categories:[{
        						id: 1751,
        						title: "Spremnik kočione tekućine",
        					},
        					{
        						id: 1761,
        						title: "Čep posude za kočionu tekućinu",
        					},
        				],
        			},
        		],
        	},    		
        ],
    },     
];

// var myJSON = JSON.stringify(menu);
// Saving the list to JSON file
var saveData = (function () { 
    var a = document.createElement("a"); 
    document.body.appendChild(a); 
    a.style = "display: none"; 
    return function (data, fileName) { 
        var json = JSON.stringify(data), 
            blob = new Blob([json], {type: "octet/stream"}), 
            url = window.URL.createObjectURL(blob); 
        a.href = url; 
        a.download = fileName; 
        a.click(); 
        window.URL.revokeObjectURL(url); 
    }; 
}()); 
  
let fileName = "groups.json"; 
 
// Download button in html create_group
document.getElementById("clickMe").onclick = function(){
	saveData(menu, fileName); 
}
