package com.nhlstenden.amazonsimulatie.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nhlstenden.amazonsimulatie.controllers.Controller;
import com.nhlstenden.amazonsimulatie.controllers.SimulationController;
import com.nhlstenden.amazonsimulatie.graph.DijkstraGraph;
import com.nhlstenden.amazonsimulatie.graph.Node;
import com.nhlstenden.amazonsimulatie.models.World;
import com.nhlstenden.amazonsimulatie.views.DefaultWebSocketView;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/*
 * Dit is de hoofdklasse van de applicatie. De app werkt met Spring, een Java
 * framework welke handig is, bijvoorbeeld om een simpele server te schrijven.
 * De main methode zet de app in werking en er wordt een nieuw object gemaakt
 * van de class App. Dit gedeelte handeld Spring voor je af.
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class App extends SpringBootServletInitializer implements WebSocketConfigurer {

    /*
     * De main methode regelt het starten van de Spring applicatie. Dit gebeurd
     * middels SpringApplication.run(). Deze zorgt ervoor dat onze App gerund
     * wordt. Dit kan doordat de class App de class SpringBootServletInitializer
     * extend. Dit is een class van Spring welke een server voor ons maakt.
     * De App class is daardoor dus een server.
     */
    public static void main(String[] args) {
//        Graph graph = new Graph();
//
//
//        graph.add("SOURCE", "STELLAGE1", 1, 5, 0);
//        graph.add("STELLAGE1", "STELLAGE2", 1, 5, 5);
//        graph.add("STELLAGE2", "STELLAGE3", 1, 0,5);
//        graph.add("STELLAGE3", "STELLAGE4", 1, -5, 5);
//        graph.add("STELLAGE4", "STELLAGE5", 1, -5, 0);
//        graph.add("STELLAGE3", "SOURCE", 1, 0, 0);
//        graph.add("STELLAGE3", "STELLAGE6", 1, 0, 20);
//        graph.add("STELLAGE6", "SOURCE", 1, 0, 0);
//
//
//        System.out.println("Graph is connected: " + graph.DepthFirstSearch());
//        System.out.println("Connected from LAX:" + graph.BreadthFirstSearch("SOURCE"));
//        System.out.println();
//
//        System.out.println(graph);
//        System.out.println(graph.edgesToString());
//
//
//        System.out.println("Source to Stellage");
//        List<String> path = graph.getPath("SOURCE", "STELLAGE6");
//        for (String each : path)
//            System.out.println(each);

        DijkstraGraph graph = new DijkstraGraph();


        Node node1 = new Node("Source", 0, 0);
        Node node2 = new Node("Stellage1", 5, 0);
        Node node3 = new Node("Stellage2", 5, 5);
        Node node4 = new Node("Stellage3", 5, 10);
         Node node5 = new Node("Stellage4", 5, 15);
        Node node6 = new Node("Stellage5", 0, 15);
        Node node7 = new Node("Stellage6", 10, 15);
        Node node8 = new Node("Stellage7", 50, 50);

        graph.addOneWayConnection(node1, node2, 1);
         graph.addOneWayConnection(node2, node3, 1);
         graph.addOneWayConnection(node3, node4, 1);
         graph.addOneWayConnection(node4, node5, 1);
         graph.addOneWayConnection(node5, node6, 1);
         graph.addOneWayConnection(node6, node8, 1);
          graph.addOneWayConnection(node7, node8, 1);

          ArrayList<Node> nodes = graph.returnShortestPathToNode("Stellage7", "Source");


          for(Node node : nodes) {
              System.out.println(node.getName() + "\n");
          }


        //SpringApplication.run(App.class, args);
    }
    
    //De App is de applicatie en heeft de controller voor de simulatie in zich
    private Controller controller;

   /*
    * De constructor wordt uitgevoerd wanneer de app wordt opgebouwd. Je zult alleen
    * geen new App() tegenkomen. Dit doet Spring namelijk al voor je bij
    * SpringApplication.run().
    */
    public App() {
        this.controller = new SimulationController(new World());
        this.controller.start();
    }

    /*
     * Dit is een standaardmethode van Spring en heeft te maken met het SpringApplication.run()
     * proces.
     */
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

    /*
     * Deze methode moet worden geïmplementeerd omdat de class App de interface
     * WebSocketConfigurer implementeerd. Deze interface maakt het mogelijk
     * om zogenaamde WebSocketHandlers te registreren in het systeem. Dit
     * zijn onderdelen in de software die binnenkomende websocket verbindingen
     * afhandelen en iets met de binnenkomende informatie doen, dan wel nieuwe
     * informatie terugsturen naar de client.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new DefaultWebSocketHandler(), "/connectToSimulation");
    }

    /*
     * Deze class is de standaard WebSocketHandler van dit systeem. Wat hier gebeurd
     * is dat elke keer wanneer een connectie wordt gemaakt met het systeem via een
     * websocket waar deze WebSocketHandler voor is geregistreerd (zie registerWebSocketHandlers),
     * dan wordt de methode afterConnectionEstablished aangeroepen.
     */
    private class DefaultWebSocketHandler extends TextWebSocketHandler {
        /*
         * Binnen deze methode wordt, wanneer een nieuwe websocket connectie wordt gemaakt met
         * de server, een nieuwe view aangemaakt voor die connectie. De view is een
         * DefaultWebSocketView en is de view die wordt gebruikt wanneer we een browser als
         * front-end gebruiken. De sesion variabele is het onderdeel waarmee we informatie
         * kunnen sturen.
         */
        @Override
        public void afterConnectionEstablished(WebSocketSession sesion) {
            controller.addView(new DefaultWebSocketView(sesion));
        }

        /*
         * Via deze methode kunnen binnenkomende berichten van een websocket worden
         * afgehandeld. De berichten zijn gekoppeld aan een bepaalde sessie. Deze methode
         * is niet in gebruik voor de standaardcode die gegeven is. Je kunt zelf wel een
         * implementatie maken.
         */
        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) {
            //Do something to handle user input here
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
            session.close(CloseStatus.SERVER_ERROR);
        }
    }
}