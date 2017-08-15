
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * @author Nick
 */
public class nDukeFinalProject extends Application {
    
    String stat = "No Challenge Loaded \n-";
    Label mStatus = new Label(stat);
    ToggleGroup mPiecesToggle = new ToggleGroup(), mChoicesToggle = new ToggleGroup();
    ToolBarPos tbp = ToolBarPos.LEFT;
    BorderPane root;
    ToolBar t;
    VBox topVbox;
    File mChallengeFile = null, mSolutionFile = null;
    Stage mPrimaryStage;
    String mGoal = "", mCurrentTest = "";
    int mTestCount = 0, mRows = 0, mCols = 0, robotTurn = 0, tapeIndex = 0, currentTestIndex = 1;
    ImageView robot;
    ImageView [][] ivArray;
    char mPassOrFail = 'F', currentTape;
    BufferedReader mChallengeReader = null;
    GridPane gp;
    AnimationTimer mTimer;
    int robotX = 6, robotY = 0;
    long TIMER_MSEC = 1000, mPreviousTime;
    MenuItem pauseMenuItem, openMenuItem, loadMenuItem, speedMenuItem;
    boolean paused = false;
    
    private MenuBar buildMenus()
    {
        //build a menu bar
        MenuBar menuBar = new MenuBar();
        
        //file menu with just a quit item for now
        Menu fileMenu = new Menu("_File");
        fileMenu.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
        
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());        
        
        MenuItem resetMenuItem = new MenuItem("_Reset");
        resetMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        resetMenuItem.setOnAction(actionEvent -> onReset());
        
        openMenuItem = new MenuItem("_Open Challenge");
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openMenuItem.setOnAction(actionEvent -> onOpenChallenge());
        
        loadMenuItem = new MenuItem("_Load Solution");
        loadMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
        loadMenuItem.setOnAction(actionEvent -> onLoadSolution());
        
        SeparatorMenuItem s = new SeparatorMenuItem();
        fileMenu.getItems().addAll(resetMenuItem, openMenuItem, loadMenuItem, s, quitMenuItem);
        
        //game menu
        Menu gameMenu = new Menu("_Game");
        gameMenu.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        
        MenuItem goMenuItem = new MenuItem("_Go");
        goMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
        goMenuItem.setOnAction(actionEvent -> onGo());
        
        pauseMenuItem = new MenuItem("_Pause");
        pauseMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        pauseMenuItem.setOnAction(actionEvent -> onPause());
        pauseMenuItem.setDisable(true);
        
        speedMenuItem = new MenuItem("Faster");
        speedMenuItem.setOnAction(actionEvent -> onSpeed(speedMenuItem));
        
        gameMenu.getItems().addAll(goMenuItem, pauseMenuItem, speedMenuItem);
        
        //help menu with just an about item for now
        Menu helpMenu = new Menu("_Help");
        helpMenu.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);        
        return menuBar;
    }
    
   
    private void onAbout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Nick B Duke, CSCD 370 Final Project, Winter 2017");
        alert.showAndWait();
    }
    
    private void setStatus(String status)
    {
        mStatus.setText(status);
    }
    
    @Override
    public void start(Stage primaryStage) 
    {   
        mPrimaryStage = primaryStage;
        mPreviousTime = 0;
        ivArray = new ImageView[13][13];
        ToolBar toolbar = createToolbar(); 
        toolbar.setPrefWidth(0);
        root = new BorderPane(); 
        root.setLeft(toolbar);
        
        //add gameboard
        gp = new GridPane();
        
        robot = new ImageView(new Image(getClass().getResourceAsStream("robot.png")));
        robot.setFitWidth(50);
        robot.setFitHeight(50);
            
        Image tile = new Image(getClass().getResourceAsStream("tile.png"));
        fillGridPane(tile);        
       
        root.setCenter(gp);
        
        //add menus
        topVbox = new VBox(buildMenus());
        root.setTop(topVbox);
        root.setBottom(mStatus);
        
        Scene scene = new Scene(root, gp.getMaxWidth(), gp.getMaxHeight());
        
        mPrimaryStage.setResizable(false);
        mPrimaryStage.setTitle("Robot Factory");
        mPrimaryStage.setScene(scene);
        mPrimaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private ToolBar createToolbar()
    {
        t = new ToolBar();
        t.setOrientation(Orientation.VERTICAL);
        
        //Arrow Button
        ToggleButton arrowButton = new ToggleButton();
        arrowButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Move.png"))));
        arrowButton.setOnAction(actionEvent -> onMove());
        arrowButton.setTooltip(new Tooltip("Move"));

        //Up Button
        ToggleButton upButton = new ToggleButton();
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("up.png")));
        iv1.setFitHeight(30);
        iv1.setFitWidth(30);
        upButton.setGraphic(iv1);
        upButton.setTooltip(new Tooltip("Up"));
        upButton.setToggleGroup(mPiecesToggle);
        upButton.setSelected(true);
        
        //Down Button
        ToggleButton downButton = new ToggleButton();
        ImageView iv2 = new ImageView(new Image(getClass().getResourceAsStream("down.png")));
        iv2.setFitHeight(30);
        iv2.setFitWidth(30);
        downButton.setGraphic(iv2);
        downButton.setTooltip(new Tooltip("Down"));
        downButton.setToggleGroup(mPiecesToggle);
        
        //Left Button
        ToggleButton leftButton = new ToggleButton();
        ImageView iv3 = new ImageView(new Image(getClass().getResourceAsStream("left.png")));
        iv3.setFitHeight(30);
        iv3.setFitWidth(30);
        leftButton.setGraphic(iv3);
        leftButton.setTooltip(new Tooltip("Left"));
        leftButton.setToggleGroup(mPiecesToggle);
        
        //Right Button
        ToggleButton rightButton = new ToggleButton();
        ImageView iv4 = new ImageView(new Image(getClass().getResourceAsStream("right.png")));
        iv4.setFitHeight(30);
        iv4.setFitWidth(30);
        rightButton.setGraphic(iv4);
        rightButton.setTooltip(new Tooltip("Right"));
        rightButton.setToggleGroup(mPiecesToggle);
                
        //Up switch
        ToggleButton upSwitchButton = new ToggleButton();
        ImageView iv5 = new ImageView(new Image(getClass().getResourceAsStream("sw_up.png")));
        iv5.setFitHeight(30);
        iv5.setFitWidth(30);
        upSwitchButton.setGraphic(iv5);
        upSwitchButton.setTooltip(new Tooltip("Up Switch"));
        upSwitchButton.setToggleGroup(mPiecesToggle);
        
        //Down switch
        ToggleButton downSwitchButton = new ToggleButton();
        ImageView iv6 = new ImageView(new Image(getClass().getResourceAsStream("sw_down.png")));
        iv6.setFitHeight(30);
        iv6.setFitWidth(30);
        downSwitchButton.setGraphic(iv6);
        downSwitchButton.setTooltip(new Tooltip("Down Switch"));
        downSwitchButton.setToggleGroup(mPiecesToggle);
        
        //Left Switch
        ToggleButton leftSwitchButton = new ToggleButton();
        ImageView iv7 = new ImageView(new Image(getClass().getResourceAsStream("sw_left.png")));
        iv7.setFitHeight(30);
        iv7.setFitWidth(30);
        leftSwitchButton.setGraphic(iv7);
        leftSwitchButton.setTooltip(new Tooltip("Left Switch"));
        leftSwitchButton.setToggleGroup(mPiecesToggle);
        
        //Right Switch
        ToggleButton rightSwitchButton = new ToggleButton();
        ImageView iv8 = new ImageView(new Image(getClass().getResourceAsStream("sw_right.png")));
        iv8.setFitHeight(30);
        iv8.setFitWidth(30);
        rightSwitchButton.setGraphic(iv8);
        rightSwitchButton.setTooltip(new Tooltip("Right Switch"));
        rightSwitchButton.setToggleGroup(mPiecesToggle);
        
        //X Button
        ToggleButton xButton = new ToggleButton();
        xButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("x.png"))));
        xButton.setTooltip(new Tooltip("X"));
        xButton.setToggleGroup(mPiecesToggle);
        
        //Go button
        ToggleButton goButton = new ToggleButton();
        goButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("go.png"))));
        goButton.setOnAction(actionEvent -> onGo());
        goButton.setTooltip(new Tooltip("Go"));
        goButton.setToggleGroup(mChoicesToggle);
        
        Separator s1 = new Separator(Orientation.HORIZONTAL);
        Separator s2 = new Separator(Orientation.HORIZONTAL);
        Separator s3 = new Separator(Orientation.HORIZONTAL);
        Separator s4 = new Separator(Orientation.HORIZONTAL);
        
        t.getItems().addAll(upButton, downButton, leftButton, rightButton, s1, upSwitchButton, downSwitchButton, leftSwitchButton, rightSwitchButton, s2, xButton, s3, goButton, s4, arrowButton);
        return t;
    }

    private void onMove() 
    {
        if(tbp == ToolBarPos.LEFT)
        {
            tbp = ToolBarPos.TOP;
            t.setOrientation(Orientation.HORIZONTAL);
            root.setTop(null);
            topVbox = new VBox(buildMenus(), t);
            root.setTop(topVbox);
        }
        else if(tbp == ToolBarPos.TOP)
        {
            tbp = ToolBarPos.RIGHT;
            t.setOrientation(Orientation.VERTICAL);
            root.setRight(null);
            root.setRight(t);
        }
        else if(tbp == ToolBarPos.RIGHT)
        {
            root.setRight(null);
            tbp = ToolBarPos.LEFT;
            t.setOrientation(Orientation.VERTICAL);
            root.setTop(null);
            root.setTop(buildMenus());
            root.setLeft(null);
            root.setLeft(t);
        }
    }
    
    private void fillGridPane(Image tile) 
    {
        for(int x = 0; x < 13; x++)
        {
            for(int y = 0; y < 13; y++)
            {       
               ImageView iv = new ImageView(tile);
               iv.setUserData("0");
               iv.setOnMouseClicked(new EventHandler<MouseEvent>() 
               {
                @Override
                public void handle(MouseEvent event) 
                {
                    MouseButton button = event.getButton();
                  if(button == MouseButton.PRIMARY)
                  {    
                    ObservableList<Toggle> list = mPiecesToggle.getToggles();
                    if(list.get(0).isSelected())//up
                    {
                        iv.setImage(new Image(getClass().getResourceAsStream("up.png")));
                        iv.setUserData("3");
                    }
                    else if(list.get(1).isSelected())//down
                    {
                        iv.setImage(new Image(getClass().getResourceAsStream("down.png")));
                        iv.setUserData("4");
                    }
                    else if(list.get(2).isSelected())//left
                    {
                        iv.setImage(new Image(getClass().getResourceAsStream("left.png")));
                        iv.setUserData("5");
                    }
                    else if(list.get(3).isSelected())//right
                    {
                        iv.setImage(new Image(getClass().getResourceAsStream("right.png")));
                        iv.setUserData("6");
                    }
                    else if(list.get(4).isSelected())//up switch
                    {
                        iv.setImage(new Image(getClass().getResourceAsStream("sw_up.png")));
                        iv.setUserData("7");
                    }
                    else if(list.get(5).isSelected())//down switch
                    {
                        iv.setImage(new Image(getClass().getResourceAsStream("sw_down.png")));
                        iv.setUserData("8");
                    }
                    else if(list.get(6).isSelected())//left switch
                    {
                        iv.setImage(new Image(getClass().getResourceAsStream("sw_left.png")));
                        iv.setUserData("9");
                    }
                    else if(list.get(7).isSelected())//right switch
                    {
                        iv.setImage(new Image(getClass().getResourceAsStream("sw_right.png")));  
                        iv.setUserData("10");
                    }
                   else if(list.get(8).isSelected())//delete
                   {
                        iv.setImage(new Image(getClass().getResourceAsStream("tile.png")));
                        iv.setUserData("0");
                   }
              }
              else
              {
                  iv.setImage(new Image(getClass().getResourceAsStream("tile.png")));
                  iv.setUserData("0");
              }     
              iv.setFitHeight(50);
              iv.setFitWidth(50);  
              }
              });//end handle
            gp.add(iv, x, y);
            ivArray[x][y] = iv;
            }
        }        
        Image source = new Image(getClass().getResourceAsStream("src.png"));
        Image sink = new Image(getClass().getResourceAsStream("sink.png"));
        ImageView s1 = new ImageView(source), s2 = new ImageView(sink);
        s1.setUserData("1");
        s2.setUserData("2");
        ivArray[6][0] = s1;
        ivArray[6][12] = s2;
        GridPane.setHalignment(s1, HPos.CENTER);
	GridPane.setValignment(s1, VPos.CENTER);
        gp.add(s1, 6, 0);
        GridPane.setHalignment(s2, HPos.CENTER);
	GridPane.setValignment(s2, VPos.CENTER);
        gp.add(s2, 6, 12);
        GridPane.setHalignment(robot, HPos.CENTER);
	GridPane.setValignment(robot, VPos.CENTER);
        gp.add(robot, 6, 0);
    }

    private void onGo() 
    {
        if(mChallengeFile == null)//no challenge file loaded
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sorry!");
            alert.setHeaderText("Please load a challenge file first");
            alert.show();
        }
        else if(paused == true)
        {
            mTimer.start();
        }
        else
        {
                openMenuItem.setDisable(true);
                loadMenuItem.setDisable(true);
                pauseMenuItem.setDisable(false);
                try
                {
                    gp.getChildren().remove(robot);
                }
                catch(Exception e)
                {
                    
                }
                
                robot = new ImageView(new Image(getClass().getResourceAsStream("robot.png")));
                robot.setFitHeight(50);
                robot.setFitWidth(50);
                mTimer = new AnimationTimer() {
                @Override
                public void handle(long now)
                {    
                    onTimer(now);
                }
                };
                mTimer.start();
              try
              {        
                  
                //String temp = mChallengeReader.readLine();
                //mPassOrFail = temp.charAt(0);
                //tapeIndex += 1;
                //mCurrentTest = mChallengeReader.readLine();
              }
              catch(Exception e)
              {
                 System.out.println("Improperly formatted file");  
              }
              //currentTestIndex += 1;
            }  
    }
    
    private void onTimer(long now) 
    {
        now = System.currentTimeMillis();
        long elapsed = (now - mPreviousTime);
        if(elapsed > TIMER_MSEC) 
        {
            mPreviousTime = now;
            moveRobot();
        }
    }

    private void onPause() 
    {
      try
      {
        mTimer.stop();
        paused = true;
      }
      catch(Exception e)
      {
          
      }
    }

    private void onSpeed(MenuItem menuItem) 
    {
        if(menuItem.getText().equals("Faster"))
        {
            TIMER_MSEC = 100;
            menuItem.setText("Slower");
        }
        else
        {
            TIMER_MSEC = 1000;
            menuItem.setText("Faster");
        }
    }

    private void onReset() 
    {
        try
        {
           mTimer.stop();
           openMenuItem.setDisable(false);
           loadMenuItem.setDisable(false);
           pauseMenuItem.setDisable(true);
        }
        catch(Exception e)
        {
            
        }
        gp.getChildren().remove(robot);
        robot = new ImageView(new Image(getClass().getResourceAsStream("robot.png")));
        robot.setFitHeight(50);
        robot.setFitWidth(50);
        fillGridPane(new Image(getClass().getResourceAsStream("tile.png")));
    }

    private void onOpenChallenge()//.rbt 
    {
        try
        {
            gp.getChildren().remove(robot);
            robot = new ImageView(new Image(getClass().getResourceAsStream("robot.png")));
            robot.setFitHeight(50);
            robot.setFitWidth(50);
            robotX = 6;
            robotY = 0;
            gp.add(robot, robotX, robotY);
        }
        catch(Exception e)
        {
            
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a .rbt File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter(".rbt Files", "*.rbt"));
        mChallengeFile = fileChooser.showOpenDialog(mPrimaryStage);
        
        if(mChallengeFile == null)
                return;
        try
        {
            tapeIndex = 0;
            FileReader fr = new FileReader(mChallengeFile);
            mChallengeReader = new BufferedReader(fr);
            mGoal = mChallengeReader.readLine();
            mTestCount = mChallengeReader.read(); 
            mChallengeReader.readLine();
            mPassOrFail = (char) mChallengeReader.read();
            mChallengeReader.readLine();
            mCurrentTest = mChallengeReader.readLine();
            currentTape = mCurrentTest.charAt(tapeIndex);
        }
        catch(Exception e)
        {
            
        }
        mPrimaryStage.setTitle(mChallengeFile.getName());
        setStatus(mGoal + "\n" + ">" + mCurrentTest + "\n");
    }

    private void onLoadSolution()//.grd 
    {
        robot = new ImageView("robot.png");
        robot.setFitHeight(50);
        robot.setFitWidth(50);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a .grd File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter(".grd Files", "*.grd"));
        mSolutionFile = fileChooser.showOpenDialog(mPrimaryStage);
        
        //fill board
        int type = 0;
        try
        {
            FileReader fr = new FileReader(mSolutionFile);
            BufferedReader br = new BufferedReader(fr);
            String rows = br.readLine();
            mRows = Integer.parseInt(rows);
            String cols = br.readLine();
            mCols = Integer.parseInt(cols);
            
            for(int x = 0; x < mRows; x++)
            {
                String line = br.readLine();
                String [] array = line.split("\\s");
                for(int y = 0; y < mCols; y++)
                {
                    type = Integer.parseInt(array[y]);//0-8
                    addPiece(type, y, x);
                }
            }
        }
        catch(Exception e)
        {
            setStatus("Invalid solution file");
        }
        mPrimaryStage.setTitle(mSolutionFile.getName());
    }

    private void addPiece(int type, int row, int col) 
    {
        switch(type)
        {
            case 0:
                ivArray[row][col].setImage(new Image(getClass().getResourceAsStream("tile.png")));
                ivArray[row][col].setUserData("0");
                break;
            case 1:
                ImageView src = new ImageView(new Image(getClass().getResourceAsStream("src.png")));
                ivArray[row][col].setUserData("1");
                GridPane.setHalignment(src, HPos.CENTER);
                GridPane.setValignment(src, VPos.CENTER);
                gp.add(src, row, col);
                gp.getChildren().remove(robot);
                gp.add(robot, row, col);
                break;
            case 2:
                ImageView sink = new ImageView(new Image(getClass().getResourceAsStream("sink.png")));
                ivArray[row][col].setUserData("2");
                GridPane.setHalignment(sink, HPos.CENTER);
                GridPane.setValignment(sink, VPos.CENTER);
                gp.add(sink, row, col);
                break;
            case 3:
                ivArray[row][col].setImage(new Image(getClass().getResourceAsStream("up.png")));
                ivArray[row][col].setUserData("3");
                break;
            case 4:
                ivArray[row][col].setImage(new Image(getClass().getResourceAsStream("down.png")));
                ivArray[row][col].setUserData("4");
                break;
            case 5:
                ivArray[row][col].setImage(new Image(getClass().getResourceAsStream("left.png")));
                ivArray[row][col].setUserData("5");
                break;
            case 6:
                ivArray[row][col].setImage(new Image(getClass().getResourceAsStream("right.png")));
                ivArray[row][col].setUserData("6");
                break;
            case 7:
                ivArray[row][col].setImage(new Image(getClass().getResourceAsStream("sw_up.png")));
                ivArray[row][col].setUserData("7");
                break;
            case 8:
                ivArray[row][col].setImage(new Image(getClass().getResourceAsStream("sw_down.png")));
                ivArray[row][col].setUserData("8");
                break;
        }
       if(type != 1 && type != 2)
       {
            ivArray[row][col].setFitHeight(50);
            ivArray[row][col].setFitWidth(50);
       } 
    }

    public void moveRobot() 
    {
        gp.getChildren().remove(robot);
        ImageView iv = ivArray[robotX][robotY];
        
        switch((String) iv.getUserData())
        {
            case "0":
                mTimer.stop();
                openMenuItem.setDisable(false);
                loadMenuItem.setDisable(false);
                pauseMenuItem.setDisable(true);
                if(mPassOrFail == 'F')
                {
                    showSuccessAlert();
                }
                else
                {
                    showFailureAlert();
                }
                
                break;
            case "1":
                if(currentTape == 'B')
                {
                    robot = new ImageView(new Image(getClass().getResourceAsStream("bluerobot.png")));
                }
                else
                {
                    robot = new ImageView(new Image(getClass().getResourceAsStream("redrobot.png")));
                }
                robot.setFitWidth(50);
                robot.setFitHeight(50);
                
                
                robotY+=1;
                gp.add(robot, robotX, robotY);
                break;
            case "2":
                mTimer.stop();
                openMenuItem.setDisable(false);
                loadMenuItem.setDisable(false);
                pauseMenuItem.setDisable(true);
                break;
            case "3":
                robotY -= 1;
                gp.add(robot, robotX, robotY);
                break;
            case "4":
                robotY += 1;
                gp.add(robot, robotX, robotY);
                break;
            case "5":
                robotX -= 1;
                gp.add(robot, robotX, robotY);
                break;
            case "6":
                robotX += 1;
                gp.add(robot, robotX, robotY);
                break;
            case "7":
                //not using this
                break;
            case "8":
                if(currentTape == 'R')//currently red robot
                {
                    if(tapeIndex+1 < mCurrentTest.length())//more tapes left
                    {
                        if(mCurrentTest.charAt(tapeIndex+1) == 'R')//next robot is red
                        {
                            robot = new ImageView(new Image(getClass().getResourceAsStream("redrobot.png")));
                            robot.setFitWidth(50);
                            robot.setFitHeight(50);
                            robotX += 1;
                            gp.add(robot, robotX, robotY);
                            currentTape = 'R';
                        }
                        else//blue
                        {
                            robot = new ImageView(new Image(getClass().getResourceAsStream("bluerobot.png")));
                            robot.setFitWidth(50);
                            robot.setFitHeight(50);
                            robotX += 1;
                            currentTape = 'B';
                            gp.add(robot, robotX, robotY);
                        }
                    }
                    else
                    {
                        robot = new ImageView(new Image(getClass().getResourceAsStream("robot.png")));
                        robot.setFitWidth(50);
                        robot.setFitHeight(50);
                        robotY += 1;
                        currentTape = 'Z';
                        gp.add(robot, robotX, robotY);
                    }
                }
                else if(currentTape == 'B')
                {
                    if(tapeIndex+1 < mCurrentTest.length())//more tapes left
                    {
                        if(mCurrentTest.charAt(tapeIndex+1) == 'R')//next robot is red
                        {
                            robot = new ImageView(new Image(getClass().getResourceAsStream("redrobot.png")));
                            robot.setFitWidth(50);
                            robot.setFitHeight(50);
                            robotX -= 1;
                            currentTape = 'R';
                            gp.add(robot, robotX, robotY);
                        }
                        else//blue
                        {
                            robot = new ImageView(new Image(getClass().getResourceAsStream("bluerobot.png")));
                            robot.setFitWidth(50);
                            robot.setFitHeight(50);
                            robotX -= 1;
                            currentTape = 'B';
                            gp.add(robot, robotX, robotY);
                        }
                    }
                    else
                    {
                        robot = new ImageView(new Image(getClass().getResourceAsStream("robot.png")));
                        robot.setFitWidth(50);
                        robot.setFitHeight(50);
                        robotX -= 1;
                        currentTape = 'Z';
                        gp.add(robot, robotX, robotY);
                    }
                }
                else if(currentTape == 'Z')//blank robot
                {
                    robotY += 1;
                    gp.add(robot, robotX, robotY);
                }
                tapeIndex += 1;
                
                //alter status
                String[] temp = mStatus.getText().split(">");
                String s1 = temp[0];
                //System.out.println(s1);
                String s2 = temp[1];
                String str;
                if(s2.substring(0, 1).equals("\n"))
                {
                    str = s1 + ">";
                }
                else
                {
                    str = s1 + s2.substring(0, 1) + ">";
                }    
                if(s2.length() > 1)
                {
                    str += s2.substring(1);
                }
                setStatus(str);
                break;    
        }
    }

    private void showSuccessAlert() 
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("SO FAR SO GOOD");
        alert.setHeaderText("Passed test: " + currentTestIndex);
        alert.show();
    }

    private void showFailureAlert() 
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("FAILURE");
        alert.setHeaderText("Failed test " + currentTestIndex + ", try a new solution");
        alert.show();
    }

    public enum ToolBarPos
    {
        LEFT, TOP, RIGHT;
    }
}
