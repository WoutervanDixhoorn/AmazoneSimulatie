function buildWarehouse (scene){

	// warehouse floor
	var geometry = new THREE.PlaneGeometry(30, 30);
	var material = new THREE.MeshPhongMaterial({ map: new THREE.TextureLoader().load("textures/floor.jpg"), side: THREE.DoubleSide });
	var plane = new THREE.Mesh(geometry, material);
	plane.rotation.x = Math.PI / 2.0;
	plane.position.x = 15;
	plane.position.z = 15;
	plane.receiveShadow = true;
	scene.add(plane);

//warehouse
	var geometry = new THREE.BoxGeometry(31, 5, 1);
	var material = new THREE.MeshPhongMaterial({ map: new THREE.TextureLoader().load("textures/material_wood.jpeg"), side: THREE.DoubleSide });

	//wall left
	var wall1 = new THREE.Mesh(geometry, material);
	wall1.position.x = 15;
	wall1.position.y = 2.5;
	wall1.position.z = 0;
	wall1.castShadow = true;
	wall1.receiveShadow = true;

	//wall right
	var wall2 = new THREE.Mesh(geometry, material);
	wall2.position.x = 15;
	wall2.position.y = 2.5;
	wall2.position.z = 30;
	wall2.castShadow = true;
	wall2.receiveShadow = true;

	//wall back
	var wall3 = new THREE.Mesh(geometry, material);
	wall3.rotation.y = Math.PI / 2.0;
	wall3.position.x = 30;
	wall3.position.y = 2.5;
	wall3.position.z = 15;
	wall3.castShadow = true;
	wall3.receiveShadow = true;

	//front left
	var geometry2 = new THREE.BoxGeometry(10, 5, 1);
	var wall4 = new THREE.Mesh(geometry2, material);
	wall4.rotation.y = Math.PI / 2.0;
	wall4.position.x = 0;
	wall4.position.y = 2.5;
	wall4.position.z = 5;
	wall4.castShadow = true;
	wall4.receiveShadow = true;

	var geometry2 = new THREE.BoxGeometry(10, 5, 1);
	var wall5 = new THREE.Mesh(geometry2, material);
	wall5.rotation.y = Math.PI / 2.0;
	wall5.position.x = 0;
	wall5.position.y = 2.5;
	wall5.position.z = 25;
	wall5.castShadow = true;
	wall5.receiveShadow = true;

	//add walls
	var warehouseWall = new THREE.Group();
	warehouseWall.add(wall1);
	warehouseWall.add(wall2);
	warehouseWall.add(wall3);
	warehouseWall.add(wall4);
	warehouseWall.add(wall5);
	scene.add(warehouseWall);

	//roof left
	var geometryL = new THREE.BoxGeometry(32, 20,1);
	var material = new THREE.MeshPhongMaterial({  map: new THREE.TextureLoader().load("textures/material_roof.jpg"), side: THREE.DoubleSide });
	var roofLeft = new THREE.Mesh(geometryL, material);
	roofLeft.rotation.x = Math.PI / 3;
	roofLeft.position.x = 15;
	roofLeft.position.y = 8;
	roofLeft.position.z = 5;
	roofLeft.receiveShadow = true;
	roofLeft.castShadow = true;

	//roof right
	var geometryR = new THREE.BoxGeometry(32, 1,20);
	var roofRight = new THREE.Mesh(geometryR, material);
	roofRight.rotation.x = Math.PI / 6;
	roofRight.position.x = 15;
	roofRight.position.y = 8;
	roofRight.position.z = 25;
	roofRight.receiveShadow = true;
	roofRight.castShadow = true;

	//roof center top
	var geometryRoofT = new THREE.BoxGeometry(32.5, 1,5);
	var roofTop = new THREE.Mesh(geometryRoofT, material);
	roofTop.position.x = 15;
	roofTop.position.y = 13;
	roofTop.position.z = 15;
	roofTop.castShadow = true;
	roofTop.receiveShadow = true;

	//wall roof area front and back
	var material = new THREE.MeshStandardMaterial({  map: new THREE.TextureLoader().load("textures/material_wood.jpeg"), side: THREE.DoubleSide });

	for( var i = 0; i < 9; i ++ ){
		//front wall roof area
		var geoRoofFront = new THREE.BoxGeometry(0.9, 0.9, 5 + (3*i));
		var roofF = new THREE.Mesh(geoRoofFront, material);
		roofF.position.x = 0 ;
		roofF.position.y = 12 - (i*0.9);
		roofF.position.z = 15 ;
		roofF.receiveShadow = true;
		roofF.castShadow = true;
		scene.add(roofF);

		// //back wall roof area
		var geoRoofBack = new THREE.BoxGeometry(0.9, 0.9, 5 + (3*i));
		var roofB = new THREE.Mesh(geoRoofBack, material);
		roofB.position.x = 30;
		roofB.position.y = 12 - (i*0.9);
		roofB.position.z = 15 ;
		roofB.receiveShadow = true;
		roofB.castShadow = true;
		scene.add(roofB);
	}

	var warehouseRoof = new THREE.Group();
	warehouseRoof.add(roofLeft);
	warehouseRoof.add(roofRight);
	warehouseRoof.add(roofTop);
	scene.add(warehouseRoof);

	buildWorld(scene);
	buildWarehouseLighting(scene);

}

function buildWorld(scene){
	//plane outside
	var geometry = new THREE.PlaneGeometry(5, 5);
	var material = new THREE.MeshStandardMaterial({ map: new THREE.TextureLoader().load("textures/grass.jpg"), side: THREE.DoubleSide });

	for ( var j = 0; j < 25; j ++ ) {

		for( var i = 0; i < 25; i ++ ){
			var planeOutside = new THREE.Mesh(geometry, material);    
			planeOutside.rotation.x = Math.PI / 2.0;
			planeOutside.position.x = -45 + (i*5);
			planeOutside.position.y = -0.001;
			planeOutside.position.z = -45 + (j*5); 
			planeOutside.receiveShadow = true;
			scene.add(planeOutside);
		}
	}

	buildSunLight(scene, planeOutside);
}