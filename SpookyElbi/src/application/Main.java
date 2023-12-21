package application;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import weapons.Paper;
import weapons.Pen;
import weapons.Pistol;
import weapons.Shotgun;
import weapons.Weapon;
import powerups.Pet;
import powerups.Cat;
import powerups.Dog;

public class Main extends Application {
	private Scene mainMenuScene;
	private Scene settingScene;
	private Scene aboutgameScene;
	private Scene weaponShopScene;
	private Scene petShopScene;
	private Scene boostShopScene;
	private GameTimer spookyElbi;
	private MediaPlayer menuSoundtrackPlayer;
	
	private Shop shop = new Shop();
	private Weapon selectedWeapon = new Paper();
	private int selectedWeaponInt = Shop.PAPER;
	private Pet selectedPet = new Cat();
	private int selectedPetInt = Shop.CAT;
	
	private Text gems = new Text();
	private AnchorPane weaponShopButtons = new AnchorPane();
	private AnchorPane petShopButtons = new AnchorPane();
	private AnchorPane boostShopButtons = new AnchorPane();
	

	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Spooky ELBI");

		// Main Menu, first scene to show
        StackPane menuLayout = new StackPane();

        Image menuBackground = new Image("scenes\\Menu.png", 1200, 900, true, true);

        ImageView imageViewofMenuBackground = new ImageView();
        imageViewofMenuBackground.setPreserveRatio(true);
        imageViewofMenuBackground.setImage(menuBackground);

        // Load the sound file
        String menuSoundFile = "menu.mp3";
        Media menuSoundtrack = new Media(getClass().getResource(menuSoundFile).toString());
        menuSoundtrackPlayer = new MediaPlayer(menuSoundtrack);
        primaryStage.setOnShown(e -> menuSoundtrackPlayer.play());

        // AnchorPane for easier positioning of the buttons
        AnchorPane mainmenuButtons = new AnchorPane();

        // Start button
        Button startButton = createButton(
        	e -> {
	        	this.spookyElbi = new GameTimer(primaryStage, this);
	        	primaryStage.setScene(this.weaponShopScene);
	        	this.updateGems();
	        	this.addText(this.weaponShopButtons);
        	}
    	);

        // Settings button
        Button settingsButton = createButton(
    		e -> primaryStage.setScene(settingScene)
		);

        // About game button
        Button aboutgameButton = createButton(
    		e -> primaryStage.setScene(aboutgameScene)
		);

        // Exit game button
        Button exitButton = createButton(
    		e -> System.exit(0)
		);

        /*
         * Button Positions:
         *
         * (Button name, top 'margin', right 'margin', bottom 'margin', left 'margin)
         *
         * - if the button have left margin == 10 && right margin == 10
         * - then the button will 'expand' to satisfy the condition
         *
         * |   [ BUTTON]   |  -->>  | [ B U T T O N ] |
         *
         */
        // start button position
        setSpecificButtonPosition(startButton, 20, 460, 600, 500);

        // settingsPane button position
        setSpecificButtonPosition(settingsButton, 95, 420, 525, 480);

        // about game button position
        setSpecificButtonPosition(aboutgameButton, 165, 390, 460, 450);

        // exit button position
        setSpecificButtonPosition(exitButton, 230, 495, 390, 555);

        // AnchorPane mainmenuButtons for positions of the buttons
        mainmenuButtons.getChildren().addAll(startButton, settingsButton, aboutgameButton, exitButton);

        // StackPane for mainmenu layout
        menuLayout.getChildren().addAll(
        	imageViewofMenuBackground, mainmenuButtons
        );

        // mainMenuScene 1st scene after running the code
        mainMenuScene = new Scene(menuLayout, 1200, 670);

        // =================== Start of Settings ===================
        StackPane settingsPane = new StackPane();

        // settingsPane background image
        Image settingsBackground = createImage("scenes//settings.png");
        ImageView imageViewofSettingsBackground = new ImageView();
        imageViewofSettingsBackground.setPreserveRatio(true);
        imageViewofSettingsBackground.setImage(settingsBackground);

        // AnchorPane for easier positioning of the buttons
        AnchorPane settingsButtons = new AnchorPane();

        // previous button (back to main menu)
        Button settingsPreviousButton = createButton(
    		e -> {
        		primaryStage.setScene(mainMenuScene);
        	}
        );

        // adds volume for music
        Button musicAddVolumeButton = createButton(
    		e -> {
    			menuSoundtrackPlayer.setVolume(menuSoundtrackPlayer.getVolume() + 0.1);
    		}
    	);

        // lowers music volume
        Button musicLowerVolumeButton = createButton(
    		e -> {
    			menuSoundtrackPlayer.setVolume(menuSoundtrackPlayer.getVolume() - 0.1);
    		}
		);

        // sets volume of music to 0 || mutes music
        Button musicMuteButton = createButton(
    		e -> {
    			menuSoundtrackPlayer.setVolume(0);
    		}
    	);

        // set volume of music to 1 || unmutes music
        Button musicUnmuteButton = createButton(
    		e -> {
	        	// if music is not muted and the user clicked "on" for unmute button,
	        	// the music will retain it's volume
	        	if (menuSoundtrackPlayer.getVolume() > 0) {
	        		menuSoundtrackPlayer.getVolume();
	
	        	// if music is muted, the volume will be set to 1 if
	        	// the player clicked "on" to unmute the music
	        	} else {
	        		menuSoundtrackPlayer.setVolume(1);
	        	}
    		}
    	);

        /*
         * NOTICE:
         *
         * Change the parameter of sound buttons
         *
         *  - parameter should be for the sound effects
         *
         */

        // adds volume for sound
        Button soundAddVolumeButton = createButton(
        	e -> {
        		menuSoundtrackPlayer.setVolume(menuSoundtrackPlayer.getVolume() + 0.1);
        	}
        );

        // lowers volume for sound
        Button soundLowerVolumeButton = createButton(
        	e -> {
        		menuSoundtrackPlayer.setVolume(menuSoundtrackPlayer.getVolume() - 0.1);
        	}
        );

        // mutes sound
        Button soundMuteButton = createButton(
        	e -> {
        		menuSoundtrackPlayer.setVolume(0);
        	}
        );

        //unmutes sound
        Button soundUnmuteButton = createButton(
    		e -> {
	        	if (menuSoundtrackPlayer.getVolume() > 0) {
	        		menuSoundtrackPlayer.getVolume();
	        	} else {
	        		menuSoundtrackPlayer.setVolume(1);
	        	}
    		}
		);

        // positions for music buttons [add/lower] volume
        setSpecificButtonPosition(musicAddVolumeButton, 285, 915, 280, 60);
        setSpecificButtonPosition(musicLowerVolumeButton, 405, 915, 160, 60);

        // positions for music buttons [mute/unmute] volume
        setSpecificButtonPosition(musicUnmuteButton, 285, 630, 280, 345);
        setSpecificButtonPosition(musicMuteButton, 405, 630, 160, 345);

        // positions for sound buttons [add/lower] volume
        setSpecificButtonPosition(soundAddVolumeButton, 285, 335, 280, 640);
        setSpecificButtonPosition(soundLowerVolumeButton, 405, 335, 160, 640);

        // positions for sound buttons [mute/unmute] volume
        setSpecificButtonPosition(soundUnmuteButton, 285, 50, 280, 925);
        setSpecificButtonPosition(soundMuteButton, 405, 50, 160, 925);

        // previous button set-up
        previousButtonPosition(settingsPreviousButton);

        settingsButtons.getChildren().addAll(
    		settingsPreviousButton, 
    		musicAddVolumeButton, 
    		musicLowerVolumeButton, 
    		soundMuteButton, 
    		soundUnmuteButton, 
    		musicMuteButton, 
    		musicUnmuteButton, 
    		soundAddVolumeButton, 
    		soundLowerVolumeButton
    	);

        settingsPane.getChildren().addAll(
        	imageViewofSettingsBackground, settingsButtons
        );

        // scene for settingsPane
        settingScene = new Scene(settingsPane, 1200, 670);
        //=================== End of Settings ===================

        // =================== Start of About Game ===================
        StackPane aboutgamePane = new StackPane();

        // about game background image
        Image aboutgameBackground = createImage("scenes//aboutgameScene.png");
        ImageView imageViewofAboutGameBackground = new ImageView();
        imageViewofAboutGameBackground.setPreserveRatio(true);
        imageViewofAboutGameBackground.setImage(aboutgameBackground);


        AnchorPane aboutgameButtons = new AnchorPane();

        // previous button (back to main menu)
        Button aboutgamePreviousButton = createButton(
    		e -> {
    			primaryStage.setScene(mainMenuScene);
    		}
		);

        // previous button set-up
        previousButtonPosition(aboutgamePreviousButton);

        aboutgameButtons.getChildren().addAll(aboutgamePreviousButton);

        aboutgamePane.getChildren().addAll(
        	imageViewofAboutGameBackground, aboutgameButtons
        );

        // scene for about game
        aboutgameScene = new Scene(aboutgamePane, 1200, 670);
        //=================== End of About Game ===================

        // =================== Start of Weapon Screen ===================
        StackPane weaponShopPane = new StackPane();

        // weapon scene background image
        Image weaponShopBackground = createImage("scenes//weaponScene.png");
        ImageView imageViewofWeaponShopBackground = new ImageView();
        imageViewofWeaponShopBackground.setPreserveRatio(true);
        imageViewofWeaponShopBackground.setImage(weaponShopBackground);

        // layout for buttons
        weaponShopButtons = new AnchorPane();

        // previous button (back to main menu)
        Button weaponShopPreviousButton = createButton(
        	e -> {
        		primaryStage.setScene(mainMenuScene);
        	}
    	);

        /*
         * NOTICE:
         *
         * Change the parameter of equip and buy button
         *
         */
        Button weaponShopEquipButton = createButton(
        	e -> {
        		if (this.shop.getWeaponsUnlocked().contains(this.selectedWeaponInt)) {
        			this.spookyElbi.setWeapon(this.selectedWeapon);
        		}
        	}
    	);
        Button weaponShopBuyButton = createButton(
    		e -> {
    			this.shop.buyWeapon(this.selectedWeaponInt);
    			this.updateGems();
    		}
		);

        /*
        * NOTICE:
        *
        * Change the parameter of the buttons
        *
        */
		Button crumpledPaperButton = createButton(
			e -> {
				this.selectedWeapon = new Paper();
				this.selectedWeaponInt = Shop.PAPER;
			}
		);
		Button penButton = createButton(
			e -> {
				this.selectedWeapon = new Pen();
				this.selectedWeaponInt = Shop.PEN;
			}
		);
	    Button pistolButton = createButton(
			e -> {
				this.selectedWeapon = new Pistol();
				this.selectedWeaponInt = Shop.PISTOL;
			}
		);
		Button shotgunButton = createButton(
			e -> {
				this.selectedWeapon = new Shotgun();
				this.selectedWeaponInt = Shop.SHOTGUN;
			}
		);

        // next button (to pet scene)
        Button weaponShopNextButton = createButton(
    		e -> {
    			primaryStage.setScene(petShopScene);
    			this.updateGems();
    			this.addText(petShopButtons);
    		}
    	);

        // previous button set-up
        previousButtonPosition(weaponShopPreviousButton);

        // equip button set-up
        equipButtonPosition(weaponShopEquipButton);

        // buy button set-up
        buyButtonPosition(weaponShopBuyButton);

        // next button set-up
        nextButtonPosition(weaponShopNextButton);

        // weapon buttons
        setSpecificButtonPosition(crumpledPaperButton, 185, 975, 315, 55);
        setSpecificButtonPosition(penButton, 185, 670, 315, 360);
        setSpecificButtonPosition(pistolButton, 185, 355, 315, 675);
        setSpecificButtonPosition(shotgunButton, 185, 45, 315, 985);

        weaponShopButtons.getChildren().addAll(
    		weaponShopPreviousButton, 
    		weaponShopNextButton, 
    		weaponShopEquipButton, 
    		weaponShopBuyButton, 
    		crumpledPaperButton, 
    		penButton, 
    		pistolButton, 
    		shotgunButton
		);

        weaponShopPane.getChildren().addAll(
        	imageViewofWeaponShopBackground, 
        	weaponShopButtons
        );

        weaponShopScene = new Scene(weaponShopPane, 1200, 670);
        //=================== End of Weapon Screen ===================

        // =================== Start of Pet Screen ===================
        StackPane petShopPane = new StackPane();

        // pet shop background image
        Image petshopBackground = createImage("scenes//petScene.png");
        ImageView imaeViewofPetShopBackground = new ImageView();
        imaeViewofPetShopBackground.setPreserveRatio(true);
        imaeViewofPetShopBackground.setImage(petshopBackground);

        // layout for buttons
        petShopButtons = new AnchorPane();

        // previous button (back to weapon scene)
        Button petshopPreviousButton = createButton(
    		e -> {
    			primaryStage.setScene(weaponShopScene);
    			this.updateGems();
    			this.addText(weaponShopButtons);
    		}
		);

        /*
         * NOTICE:
         *
         * Change the parameter of equip and buy button
         *
         */
        Button petshopEquipButton = createButton(
    		e -> {
    			if (this.shop.getPetsUnlocked().contains(this.selectedPetInt)) {
    				this.spookyElbi.setPet(this.selectedPet);
    			}
			}
		);
        Button petshopBuyButton = createButton(
    		e -> {
    			this.shop.buyPet(this.selectedPetInt);
    			this.updateGems();
    		}
		);

        /*
         * NOTICE:
         *
         * Change the parameter of the buttons
         *
         */
        Button catButton = createButton(
        	e -> {
        		this.selectedPet = new Cat();
        		this.selectedPetInt = Shop.CAT;
        	}
    	);
        Button dogButton = createButton(
    		e -> {
    			this.selectedPet = new Dog();
    			this.selectedPetInt = Shop.DOG;
    		}
		);

        // next button (to boosts scene)
        Button petshopNextButton = createButton(
    		e -> {
    			primaryStage.setScene(boostShopScene);
    			this.updateGems();
    			this.addText(boostShopButtons);
    		}
		);

        // previous button set-up
        previousButtonPosition(petshopPreviousButton);

        // equip button set-up
        equipButtonPosition(petshopEquipButton);

        // buy button set-up
        buyButtonPosition(petshopBuyButton);

        // next button set-up
        nextButtonPosition(petshopNextButton);

        // pet buttons
        setSpecificButtonPosition(catButton, 185, 975, 315, 55);
        setSpecificButtonPosition(dogButton, 185, 670, 315, 360);

        petShopButtons.getChildren().addAll(
    		petshopPreviousButton, 
    		petshopNextButton, 
    		petshopEquipButton, 
    		petshopBuyButton, 
    		catButton, 
    		dogButton
		);

        petShopPane.getChildren().addAll(
        	imaeViewofPetShopBackground, 
        	petShopButtons
        );

        // scene for pet shop
        petShopScene = new Scene(petShopPane, 1200, 670);
        // =================== End of Pet Screen ===================

        // =================== Start of Boost Screen ===================
        StackPane boostShopPane = new StackPane();

        // weapon Scene image
        Image bg4 = createImage("scenes//boostScene.png");
        ImageView imageViewofBoostShopBackground = new ImageView();
        imageViewofBoostShopBackground.setPreserveRatio(true);
        imageViewofBoostShopBackground.setImage(bg4);

        // layout for buttons
        boostShopButtons = new AnchorPane();

        // previous button (back to pet scene)
        Button boostShopPreviousButton = createButton(
    		e -> {
    			primaryStage.setScene(petShopScene);
    			this.updateGems();
    			this.addText(petShopButtons);
    		}
		);

        /*
         * NOTICE:
         *
         * Change the parameter of equip and buy button
         *
         */
        Button boostShopEquipButton = createButton(
        	e -> {
        		primaryStage.setScene(petShopScene);
        	}
    	);
        Button boostShopBuyButton = createButton(
        	e -> {
        		primaryStage.setScene(petShopScene);
        	}
    	);

        /*
         * NOTICE:
         *
         * Change the parameter of the buttons
         *
         */
        Button coffeeButton = createButton(
    		e -> {
    			primaryStage.setScene(petShopScene);
    		}
		);
        Button crushButton = createButton(
    		e -> {
    			primaryStage.setScene(petShopScene);
    		}
		);
        Button organizationButton = createButton(
    		e -> {
    			primaryStage.setScene(petShopScene);
    		}
		);

        // next button (start game)
        Button boostShopNextButton = createButton(
        	e -> {
        		this.spookyElbi.runSpookyElbi();
        		menuSoundtrackPlayer.stop();
        	}
        ); // game scene

        // previous button set-up
        previousButtonPosition(boostShopPreviousButton);

        // equip button set-up
        equipButtonPosition(boostShopEquipButton);

        // buy button set-up
        buyButtonPosition(boostShopBuyButton);

        // next button set-up
        nextButtonPosition(boostShopNextButton);

        // boost buttons
        setSpecificButtonPosition(coffeeButton, 185, 975, 315, 55);
        setSpecificButtonPosition(crushButton, 185, 670, 315, 360);
        setSpecificButtonPosition(organizationButton, 185, 355, 315, 675);

        boostShopButtons.getChildren().addAll(
    		boostShopPreviousButton, 
    		boostShopNextButton, 
    		boostShopEquipButton, 
    		boostShopBuyButton, 
    		coffeeButton, 
    		crushButton, 
    		organizationButton
		);

        boostShopPane.getChildren().addAll(
    		imageViewofBoostShopBackground, 
    		boostShopButtons
        );

        boostShopScene = new Scene(boostShopPane, 1200, 670);
        //=================== End of Boost Screen ===================
        //=================== Game ===================



        // //=================== End Game ===================



        // Show program
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

	}

	// creates image
	private Image createImage(String filename){
		Image pic = new Image(filename, 1200, 900, true, true);
		return pic;

	}

	// creates button
	private Button createButton(EventHandler<ActionEvent> handler) {
		Button button = new Button();
		button.setStyle("-fx-opacity: 0;");
		button.setOnAction(handler);
		button.addEventHandler(ActionEvent.ACTION, e -> {
			Main.turnOpacity(button);
		});
		return button;
	}
	
	// next button position
	public static void nextButtonPosition(Button node) {
        AnchorPane.setTopAnchor(node, 555d);
        AnchorPane.setRightAnchor(node, 10d);
        AnchorPane.setBottomAnchor(node, 8d);
        AnchorPane.setLeftAnchor(node, 970d);
    }

	//previous button position
	public static void previousButtonPosition(Button node) {
        AnchorPane.setTopAnchor(node, 555d);
        AnchorPane.setRightAnchor(node, 970d);
        AnchorPane.setBottomAnchor(node, 8d);
        AnchorPane.setLeftAnchor(node, 10d);
    }

	//equip button position
	public static void equipButtonPosition(Button node) {
        AnchorPane.setTopAnchor(node, 555d);
        AnchorPane.setRightAnchor(node, 635d);
        AnchorPane.setBottomAnchor(node, 8d);
        AnchorPane.setLeftAnchor(node, 340d);
    }

	//buy button position
	public static void buyButtonPosition(Button node) {
		AnchorPane.setTopAnchor(node, 555d);
        AnchorPane.setRightAnchor(node, 335d);
        AnchorPane.setBottomAnchor(node, 8d);
        AnchorPane.setLeftAnchor(node, 640d);
    }

	// specific button position
	public static void setSpecificButtonPosition(Button node, double top, double right, double bot, double left) {
	    AnchorPane.setTopAnchor(node, top);
	    AnchorPane.setRightAnchor(node, right);
	    AnchorPane.setBottomAnchor(node, bot);
	    AnchorPane.setLeftAnchor(node, left);
	}
	
	private static void turnOpacity(Button node) {
		node.setStyle("-fx-opacity: 0.1;");
		Timer timer = new Timer();
		timer.schedule(
			new TimerTask() {
				@Override
				public void run() {
					node.setStyle("-fx-opacity: 0;");
					cancel();
				}
			},
			100
		);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Scene getMainMenuScene() {
		return this.mainMenuScene;
	}
	
	public MediaPlayer getMenuSoundtrackPlayer() {
		return this.menuSoundtrackPlayer;
	}
	
	private void updateGems() {
		this.gems.setText("" + this.shop.getGems());
		this.gems.setFont(Font.font("Arial", 30));
	}
	
	private void addText(AnchorPane anchorPane) {
		if (anchorPane.getChildren().contains(this.gems)) {
			return;
		}
		
		anchorPane.getChildren().add(this.gems);
		AnchorPane.setTopAnchor(this.gems, (double) 20);
        AnchorPane.setRightAnchor(this.gems, (double) 40);
	}
	
	public Shop getShop() {
		return this.shop;
	}
}
