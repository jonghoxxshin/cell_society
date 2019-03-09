package app.view;

import app.controller.SimulationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.*;

public class GraphView{
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    private XYChart myXYchart;
    private Group myRoot;
    private SimulationController mySimulationController;
    private ResourceBundle myProperties;
    private ObservableList<XYChart.Series<Integer,Double>> myLineChartData;
    private int indexCount;
    private XYChart.Series<Integer, Double> mySeries0;
    private XYChart.Series<Integer, Double> mySeries1;
    private XYChart.Series<Integer, Double> mySeries2;
    public GraphView(SimulationController sc, ResourceBundle prop){
        System.out.println("graph initialized");
        ObservableList<XYChart.Series<Integer, Double>> myLineChartData =
                FXCollections.observableArrayList();



        indexCount = 0;
        mySimulationController = sc;
        myProperties = prop;
        xAxis.setAnimated(true);
        xAxis.setLabel("simulation #");
        yAxis.setAnimated(true);
        yAxis.setLabel("State %");
        myXYchart = new LineChart(xAxis, yAxis);
        mySeries0 = new XYChart.Series<Integer, Double>();
        mySeries0.setName("State 0");
        myXYchart.getData().add(mySeries0);

        mySeries1 = new XYChart.Series<Integer, Double>();
        mySeries1.setName("State 1");

        myXYchart.getData().add(mySeries1);
        mySeries2 = new XYChart.Series<Integer, Double>();
        mySeries2.setName("State 2");

        myXYchart.getData().add(mySeries2);

        myXYchart.setMaxSize(300, 300);
        myRoot = new Group(myXYchart);



    }

    public void addToData(Map<Integer, Double> input){

        ArrayList<Double> tempList = new ArrayList<>();
        for(int i : input.keySet()){
            tempList.add(input.get(i));
        }
        updateSeries(indexCount, tempList);
        indexCount++;
    }

    private void updateSeries(int index,ArrayList<Double> list){
        System.out.println("the index value is " + index);
        for(int i =0; i<list.size();i++){
            if(i==0){
                mySeries0.getData().add(new XYChart.Data<>(index, list.get(i)));
            }else if(i==1){
                mySeries1.getData().add(new XYChart.Data<>(index, list.get(i)));
            }else{
                mySeries2.getData().add(new XYChart.Data<>(index, list.get(i)));
            }
        }
    }

    public Group getMyRoot(){
        return myRoot;
    }

}
