function buildLighting(scene){
     //making the shadows lighter.
     var ambLight = new THREE.AmbientLight( 0xffffff, 0.1);
     scene.add(ambLight);



     //add all lights + housing
     var housingGeo = new THREE.BoxGeometry( 1,1,1);
     var housingMaterial = new THREE.MeshPhongMaterial({color:0x000000})
     var lightHousing = new THREE.Mesh(housingGeo, housingMaterial);
     lightHousing.position.set(10 , 10 , 10)

     var geometry = new THREE.CircleGeometry( 0.5, 32 );
     var material = new THREE.MeshBasicMaterial( { color: 0xffffff } );
     var circle = new THREE.Mesh( geometry, material );
     circle.rotation.x = Math.PI / 2.0;
     circle.position.set(10,9.45,10);

     var completeLight = new THREE.Group();
     completeLight.add(circle);
     completeLight.add(lightHousing);
     

     for ( var p2 = 0; p2 < 2; p2 ++ ) {

         for( var p1 = 0; p1 < 2; p1 ++ ){
             var instance = completeLight.clone()
             instance.position.x += p1*10;
             instance.position.z += p2*10;
             scene.add(instance);
         }
     }

    
     

     

}

function buildWarehouseLighting(scene){
    
	var color = 0xffffff;
	var intensity = 0.125;

	var light = new THREE.SpotLight( color,intensity);
	light.position.set(10 , 10 , 10);
	light.target.position.x = 10;
	light.target.position.y = 0;
	light.target.position.z = 10;
	light.castShadow = true;
	
	var light2 = new THREE.SpotLight( color,intensity);
	light2.position.set(20 , 10 , 10);
	light2.target.position.x = 20;
	light2.target.position.y = 0;
	light2.target.position.z = 10;
	light2.castShadow = true;

	var light3 = new THREE.SpotLight( color,intensity);
	light3.position.set(10 , 10 , 20);
	light3.target.position.x = 10;
	light3.target.position.y = 0;
	light3.target.position.z = 20;
	light3.castShadow = true;

	var light4 = new THREE.SpotLight( color,intensity);
	light4.position.set(20 , 10 , 20);
	light4.target.position.x = 20;
	light4.target.position.y = 0;
	light4.target.position.z = 20;
    light4.castShadow = true;
    
    scene.add ( light, light2, light3, light4);
    scene.add ( light.target, light2.target, light3.target, light4.target);
}

function buildSunLight(scene, sunTarget){
// sun light
var dLight = new THREE.DirectionalLight( 0xffffff, 0.8);
dLight.position.set(1, 25 ,0 );
dLight.target = sunTarget;
dLight.castShadow = true;
                
//proper shadow area
var d = 50;
dLight.shadow.camera.left = - d;
dLight.shadow.camera.right = d;
dLight.shadow.camera.top = d;
dLight.shadow.camera.bottom = - d;
scene.add(dLight);

}