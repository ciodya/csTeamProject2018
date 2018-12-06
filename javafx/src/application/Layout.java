/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.EventHandler;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.Clipboard;

import javafx.scene.input.ClipboardContent;

import javafx.scene.input.DragEvent;

import javafx.scene.input.MouseEvent;

import javafx.scene.input.TransferMode;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class Layout extends AnchorPane{

	@FXML SplitPane base_pane;

    @FXML AnchorPane right_pane;

    @FXML VBox left_pane;
    
    @FXML Button clearButton;
    
    @FXML Button saveButton;
    
    @FXML Button generateButton;
    
    @FXML Button exportButton;
    
    @FXML TextArea textArea;
    
    @FXML TitledPane configBox;


    private DragableNode mDragableNodeOver = null;
    private DraggableArea mDragableAreaOver = null;

    private EventHandler mNodeDragOverRoot = null;
    private EventHandler mAreaDragOverRoot = null;

    private EventHandler mNodeDragDropped = null;
    private EventHandler mAreaDragDropped = null;

    private EventHandler mNodeDragOverRightPane = null;
    private EventHandler mAreaDragOverRightPane = null;

    public Layout(){

        FXMLLoader fxmlLoader = new FXMLLoader(

            getClass().getResource("Layout.fxml")

        );

        fxmlLoader.setRoot(this);

        fxmlLoader.setController(this);

        try{
        	
            fxmlLoader.load();

        } catch(IOException exception){

            throw new RuntimeException(exception);

        }

    }


    @FXML

    private void initialize(){
    	
    	clearButton.setOnAction(e -> {

           textArea.setText(null);

        });
    	
    	saveButton.setOnAction(e -> {

//            to be fullfilled

         });
    	
    	exportButton.setOnAction(e -> {

//          to be fullfilled

         });
    	
    	generateButton.setOnAction(e -> {

            
//          to be fullfilled
         });

        mDragableNodeOver = new DragableNode();

        mDragableNodeOver.setVisible(false);

        mDragableNodeOver.setOpacity(0.65);

        getChildren().add(mDragableNodeOver);
        
        
        
        mDragableAreaOver = new DraggableArea();

        mDragableAreaOver.setVisible(false);

        mDragableAreaOver.setOpacity(0.65);

        getChildren().add(mDragableAreaOver);
        
        Label areaLabel = new Label("Area");
        Label nodeLabel = new Label("Node");
        Label linkLabel = new Label("Link");
        
        left_pane.getChildren().add(areaLabel);
        
       
        DraggableArea area = new  DraggableArea();

        addDragDetection(area);

        left_pane.getChildren().add(area);

        
        left_pane.getChildren().add(nodeLabel);

        
        DragableNode node = new DragableNode();

        addDragDetection(node);

        left_pane.getChildren().add(node);

        
        left_pane.getChildren().add(linkLabel);


        buildDragHandlers();
        
        buildDragHandlers2();

    }

//    public void saveButtonClick() {
//    	
//    }
//    
//    public void exportButtonClick() {
//    	
//    }
//    
//    public void generateButtonClick() {
//	
//    }
    
//    public void clearButtonClick() {
//    	
//    }

    private void buildDragHandlers(){



        mNodeDragOverRoot = new EventHandler<DragEvent>() {

            

            @Override

            public void handle(DragEvent event) {

                

                Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());



                if (!right_pane.boundsInLocalProperty().get().contains(p)){



                    event.acceptTransferModes(TransferMode.ANY);

                    mDragableNodeOver.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                    return;

                }



                event.consume();

            }

        };



        mNodeDragOverRightPane = new EventHandler<DragEvent>() {



            @Override

            public void handle(DragEvent event) {

                

                event.acceptTransferModes(TransferMode.ANY);



                mDragableNodeOver.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));



                event.consume();

            }

        };



        mNodeDragDropped = new EventHandler<DragEvent>() {



            @Override

            public void handle(DragEvent event) {

                

                DragableContainer container = (DragableContainer) event.getDragboard().getContent(DragableContainer.AddNode);



                container.addData("scene_coordinates", new Point2D(event.getSceneX(), event.getSceneY()));

 

                ClipboardContent content = new ClipboardContent();

                content.put(DragableContainer.AddNode, container);



                event.getDragboard().setContent(content);

                event.setDropCompleted(true);

                

            }

        };


        this.setOnDragDone(new EventHandler <DragEvent> (){



            @Override

            public void handle(DragEvent event) {



                right_pane.removeEventHandler(DragEvent.DRAG_OVER, mNodeDragOverRightPane);

                right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mNodeDragDropped);

                base_pane.removeEventHandler(DragEvent.DRAG_OVER, mNodeDragOverRoot);



                mDragableNodeOver.setVisible(false);



                DragableContainer container = (DragableContainer) event.getDragboard().getContent(DragableContainer.AddNode);



                System.out.println(container.getData().toString());



                if (container != null){

                    if (container.getValue("scene_coordinates") != null){

                        

                        DragableNode nodeDropped = new DragableNode();



//                        nodeDropped.setType(DragableNodeType.valueOf(container.getValue("type")));

                        right_pane.getChildren().add(nodeDropped);



                        Point2D cursorPoint = container.getValue("scene_coordinates");



                        nodeDropped.relocateToPoint(new Point2D(cursorPoint.getX()-32, cursorPoint.getY()-32));



                    }

                }
                try{
                	
                	Stage nodeWindow = new Stage();
        			
        			BorderPane Pane = new BorderPane();
        			   
        			Scene nodeScene = new Scene(Pane, 400, 300);
        			
        			nodeWindow.setTitle("node Settings");
        			
        			nodeWindow.setScene(nodeScene);
        			
        			nodeWindow.show();
        			
        			Pane.setCenter(new nodeProperty());
        			
        			
                } catch(Exception e){

                    e.printStackTrace();

                }


                event.consume();

                

            }

        });
              
       
             
    }

    private void buildDragHandlers2(){

        mAreaDragOverRoot = new EventHandler<DragEvent>() {
            
            @Override

            public void handle(DragEvent event) {
                
                Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

                if (!right_pane.boundsInLocalProperty().get().contains(p)){

                    event.acceptTransferModes(TransferMode.ANY);

                    mDragableAreaOver.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                    return;

                }

                event.consume();

            }

        };


        mAreaDragOverRightPane = new EventHandler<DragEvent>() {

            @Override

            public void handle(DragEvent event) {
                
                event.acceptTransferModes(TransferMode.ANY);

               mDragableAreaOver.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                event.consume();

            }

        };

        mAreaDragDropped = new EventHandler<DragEvent>() {

            @Override

            public void handle(DragEvent event) {
                
                DragableContainer container = (DragableContainer) event.getDragboard().getContent(DragableContainer.AddArea);

                container.addData("scene_coordinates", new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();

                content.put(DragableContainer.AddArea, container);

                event.getDragboard().setContent(content);

                event.setDropCompleted(true);
                
            }

        };


        this.setOnDragDone(new EventHandler <DragEvent> (){

            @Override

            public void handle(DragEvent event) {

            	right_pane.removeEventHandler(DragEvent.DRAG_OVER, mAreaDragOverRightPane);

            	right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mAreaDragDropped);

                base_pane.removeEventHandler(DragEvent.DRAG_OVER, mAreaDragOverRoot);

                mDragableAreaOver.setVisible(false);

                DragableContainer container = (DragableContainer) event.getDragboard().getContent(DragableContainer.AddArea);
               
                System.out.println(container.getData().toString());

                if (container != null){

                    if (container.getValue("scene_coordinates") != null){
                        
                        DraggableArea areaDropped = new DraggableArea();

//                        areaDropped.setType(DragableNodeType.valueOf(container.getValue("type")));

                        right_pane.getChildren().add(areaDropped);

                        Point2D cursorPoint = container.getValue("scene_coordinates");

                        areaDropped.relocateToPoint(new Point2D(cursorPoint.getX(), cursorPoint.getY()));
                    }

                }
//                try{
//                	
//                	Stage nodeWindow = new Stage();
//        			
//        			BorderPane Pane = new BorderPane();
//        			   
//        			Scene nodeScene = new Scene(Pane, 400, 300);
//        			
//        			nodeWindow.setTitle("node Settings");
//        			
//        			nodeWindow.setScene(nodeScene);
//        			
//        			nodeWindow.show();
//        			
//        			Pane.setCenter(new nodeProperty());
//        			
//        			
//                } catch(Exception e){
//
//                    e.printStackTrace();
//
//                }

               

                event.consume();
                
            }

        });

    }

    private void addDragDetection(DragableNode dragableNode){

        dragableNode.setOnDragDetected(new EventHandler <MouseEvent>(){

            @Override

            public void handle(MouseEvent event) {

            	base_pane.setOnDragOver(mNodeDragOverRoot);

            	right_pane.setOnDragOver(mNodeDragOverRightPane);

            	right_pane.setOnDragDropped(mNodeDragDropped);

                //get ref to clicked node

                DragableNode node = (DragableNode) event.getSource();

                //drag baby

//                mDragableNodeOver.setType(node.getType());

                mDragableNodeOver.relocateToPoint(new Point2D (event.getSceneX(),event.getSceneY()));

                ClipboardContent content = new ClipboardContent();

                DragableContainer container = new DragableContainer();

//                container.addData("type", mDragableNodeOver.getType().toString());

                content.put(DragableContainer.AddNode, container);

                mDragableNodeOver.startDragAndDrop (TransferMode.ANY).setContent(content);

                mDragableNodeOver.setVisible(true);

                mDragableNodeOver.setMouseTransparent(true);

                event.consume();

            }

        });

    }
    
   
    private void addDragDetection(DraggableArea draggableArea){

    	draggableArea.setOnDragDetected(new EventHandler <MouseEvent>(){

            @Override

            public void handle(MouseEvent event) {

            	base_pane.setOnDragOver(mAreaDragOverRoot);

            	right_pane.setOnDragOver(mAreaDragOverRightPane);

            	right_pane.setOnDragDropped(mAreaDragDropped);

                //get ref to clicked node

            	DraggableArea area = (DraggableArea) event.getSource();

                //drag baby

//                mDragableNodeOver.setType(area.getType());

                mDragableAreaOver.relocateToPoint(new Point2D (event.getSceneX(),event.getSceneY()));

                ClipboardContent content = new ClipboardContent();

                DragableContainer container = new DragableContainer();

//                container.addData("type", mDragableNodeOver.getType().toString());

                content.put(DragableContainer.AddArea, container);

                mDragableAreaOver.startDragAndDrop (TransferMode.ANY).setContent(content);

                mDragableAreaOver.setVisible(true);

                mDragableAreaOver.setMouseTransparent(true);

                event.consume();

            }

        });

    }

}