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

	var warehouseWall = new THREE.Group();

	buildWallType(15,2.5,0, "wallLeft");
	buildWallType(15,2.5,30, "wallRight");
	buildWallType(30,2.5,15, "wallBack");
	buildWallType(0, 2.5, 5, "frontLeft");
	buildWallType(0, 2.5, 25, "frontRight");

	scene.add(warehouseWall);

	function buildWallType(x, y, z, type) {
		if(type === "frontLeft" || type === "frontRight") {
			geometry = new THREE.BoxGeometry(10, 5, 1);
		}
		var wall = new THREE.Mesh(geometry, material);
		if(type !== "wallLeft" && type !== "wallRight") {
			wall.rotation.y = Math.PI / 2.0;
		}
		wall.position.x = x;
		wall.position.y = y;
		wall.position.z = z;
		wall.castShadow = true;
		wall.receiveShadow = true;

		warehouseWall.add(wall);
	}


	var warehouseRoof = new THREE.Group();
	var roofMaterial = new THREE.MeshPhongMaterial({  map: new THREE.TextureLoader().load("textures/material_roof.jpg"), side: THREE.DoubleSide });
	buildRoof(32,20,1,3,15,8,5, "left");
	buildRoof(32,1,20,6,15,8,25, "right");
	buildRoof(32.5,1,5,0,15,13,15, "center");
	scene.add(warehouseRoof);


	function buildRoof(geometryx, geometryy, geometryz, rotDeler, x, y, z, type) {
		var geometry = new THREE.BoxGeometry(geometryx, geometryy,geometryz);
		var roof = new THREE.Mesh(geometry, roofMaterial);
		if(type === "left" || type === "right") {
			roof.rotation.x = Math.PI / rotDeler;
		}
		roof.position.x = x;
		roof.position.y = y;
		roof.position.z = z;
		roof.receiveShadow = true;
		roof.castShadow = true;
		warehouseRoof.add(roof);
	}

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