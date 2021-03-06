package ES_2Sem_2021_Grupo53.ES_2Sem_2021_Grupo53;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyGUI{

	static ArrayList<String> methodOrder = new ArrayList<String>();
	static ArrayList<String> classOrder = new ArrayList<String>();
	static ArrayList<String> methodLogic = new ArrayList<String>();
	static ArrayList<String> classLogic = new ArrayList<String>();
	static ArrayList<Integer> methodThreshold = new ArrayList<Integer>();
	static ArrayList<Integer> classThreshold = new ArrayList<Integer>();
	
	
	/**
	 * Simple method to remove an item from an array
	 * 
	 * Goes trough the array copying it to an array with 1 less length copies every position
	 * except for the one where the element in that position is equal to the item.
	 * 
	 * @param array, item
	 * @return array without the item
	 */
	public static String[] remove(String[] array, String item) {
		
		String [] answer = new String[array.length -1];
		int answerIndex = 0;
		
		for(int i = 0; i < array.length; i++)
			if(!array[i].equals(item)) {
				
				answer[answerIndex] = array[i];
				answerIndex ++;
				
			}

		return answer;
		
	}
	
	/**
	 * Simple helper method to add something to the end of an array
	 * 
	 * Copies the array to another array that has 1 more length and adds the item to the last
	 * position.
	 * 
	 * @param array, item
	 * @return array with the item at the end
	 */
	public static String[] add(String[] array, String item) {
		
		String[] answer = new String[array.length +1];
		
		for(int i = 0; i < array.length; i++) answer[i] = array[i];
		
		answer[answer.length - 1] = item;
		
		return answer;
		
	}
	
	/**
	 * This method shows the rule creation page of the interface
	 * 
	 * Shows the first combo box and after the user selects one of the code smells
	 * show another combo box with the metrics that can be used for that code smell after one of the metrics is selected displays
	 * a text field to add a threshold after the text field is selected displays a combo box with the logic operators AND and OR, if one
	 * is selected show another combo box with the remaining metrics and it goes on like this until there are no more metrics to build the rule with,
	 * a combo box with the other code smell and follows the same path previously described (metrics-> text field-> logic...)
	 *  and a test rules button which calls getMetrics() and displays the CalibratePopUp.
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public static void display(final Stage primaryStage){
		
		primaryStage.setTitle("Code Smells");
		
		//First smell definition
		final GridPane gridHelper = new GridPane();
		
		String[] smells = new String[] {"is_God_Class", "is_Long_Method"};
		final ComboBox smell = new ComboBox(FXCollections.observableArrayList(smells));
		smell.setPromptText("Choose a Code Smell");
		
		gridHelper.add(smell, 0, 0);
		
		final String[] methodsMetricHelper = new String[] {"LOC_Method", "CYCLO_Method"};
		final String[] classMetricsHelper = new String[] {"LOC_Class", "NOM_Class", "WMC_Class"};	
		final String[] smellsHelper = smells;
		
		smell.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				smell.setFocusTraversable(true);
				
				final String[] classMetricsHelper2 = classMetricsHelper;
				final GridPane grid = gridHelper;
				
				if(newValue.toString().equals("is_God_Class")) {
					
					String[] smells = smellsHelper;
					smells = remove(smells, "is_God_Class");
					final String[] smells2 = smells;
					
					final ComboBox metricsHelper = new ComboBox(FXCollections.observableArrayList(classMetricsHelper2));
				
					metricsHelper.setPromptText("Choose a Metric");
					
					grid.add(metricsHelper, 0, 2);
				
					metricsHelper.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						
							 String[] classMetrics = classMetricsHelper2;
							 ComboBox metrics = metricsHelper;
							 
							if(newValue.equals("LOC_Class")) {
								
								classMetrics = remove(classMetrics,"LOC_Class");
								
								if(classOrder.size()>0) classOrder.remove(0);
								
								classOrder.add(0, "LOC_Class");
								
							}else if(newValue.equals("NOM_Class")) {
								
								classMetrics = remove(classMetrics,"NOM_Class");
								
								if(classOrder.size() > 0) classOrder.remove(0);
								
								classOrder.add(0, "NOM_Class");
								
							
							}else if(newValue.equals("WMC_Class")) {
							
								classMetrics = remove(classMetrics,"WMC_Class");
							
								if(classOrder.size()>0) classOrder.remove(0);
								
								classOrder.add(0 ,"WMC_Class");
								
							}
						
							final TextField threshold1 = new TextField();
							threshold1.setPromptText("Threshold");
							grid.add(threshold1, 1, 2);
							final String[] classMetricsHelper = classMetrics;
							
							threshold1.focusedProperty().addListener(new ChangeListener<Boolean>() {

								@Override
								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
									
									if(newValue){
										
										ComboBox logic = new ComboBox(FXCollections.observableArrayList(new String[]{"AND", "OR"}));

										logic.setPromptText("Choose a Logic operator if you'd like");
										
										grid.add(logic, 2, 2);
										
										logic.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

											String[] classMetrics = classMetricsHelper;
											
											@Override
											public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
												
												if(newValue.toString().equals("AND")) {

													if(classLogic.size() > 0) classLogic.remove(0);
													
													classLogic.add(0, "AND");

												}else if(newValue.toString().equals("OR")) {

													if(classLogic.size() > 0) classLogic.remove(0);
													
													classLogic.add(0,"OR");

												}
												
												ComboBox metric2 = new ComboBox(FXCollections.observableArrayList(classMetrics));
												
												metric2.setPromptText("Choose a Second Metric");
												
												grid.add(metric2, 3, 2);
												
												metric2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

													@Override
													public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
														
														for(int i = 0; i < classMetrics.length; i++) {
													
															if(newValue.toString().equals(classMetrics[i])) {
																
																if(classOrder.size() > 1) classOrder.remove(1);
																classOrder.add(1, newValue.toString());
																classMetrics = remove(classMetrics, classMetrics[i]);
																break;
																
															}
															
														}
														
														TextField threshold2 = new TextField();
														threshold2.setPromptText("Threshold2");
														grid.add(threshold2,4, 2);
														final TextField threshold = threshold2;
														final String[] classMetricsHelper = classMetrics;
													
														threshold2.focusedProperty().addListener(new ChangeListener<Boolean>() {

															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																
																if(newValue){
																	
																	ComboBox logic = new ComboBox(FXCollections.observableArrayList(new String[]{"AND", "OR"}));

																	logic.setPromptText("Choose a Logic operator if you'd like");
																	
																	grid.add(logic, 5, 2);
																	
																	logic.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

																		String[] classMetrics = classMetricsHelper;
																		
																		@Override
																		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
																			
																			if(newValue.toString().equals("AND")) {
																				
																				if(classLogic.size() > 1) classLogic.remove(1);
																				classLogic.add(1, "AND");

																			}else if(newValue.toString().equals("OR")) {

																				if(classLogic.size() > 1) classLogic.remove(1);
																				classLogic.add(1, "OR");

																			}
																			
																			ComboBox metric3 = new ComboBox(FXCollections.observableArrayList(classMetrics));
																			
																			metric3.setPromptText("Choose the last Metric");
																			
																			grid.add(metric3, 6, 2);
													
																			metric3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

																				@Override
																				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
																					
																					if(newValue.toString().equals(classMetrics[0])) {
																						
																						if(classOrder.size() >2) classOrder.remove(2);
																						classOrder.add(2,newValue.toString());
																						classMetrics = remove(classMetrics, newValue.toString());
																						
																					}
																					
																					final TextField threshold3 = new TextField();
																					threshold3.setPromptText("Threshold3");
																					grid.add(threshold3, 7, 2);
																					
																					threshold3.focusedProperty().addListener(new ChangeListener<Boolean>() {

																						@Override
																						public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																							
																							if(!newValue) {
																								
																								TextField threshold = threshold3;
																								
																								try {

																									int helper = Integer.parseInt(threshold.getText());
																									if(classThreshold.size() > 2) classThreshold.remove(2);
																									classThreshold.add(2, helper);

																								}catch(Exception e) {
																								
																									ErrorMessage.display("Invalid Threshold 3 for is_God_Class.");
																								
																								}

																							}
																							
																						}																						
																						
																					});
																						
																				}
																				
																			});
																			
																		}
																		
																	});
																}
																
																if(!newValue) {
																	
																	TextField threshold2 = threshold;
																	
																	try {

																		int helper = Integer.parseInt(threshold2.getText());
																		if(classThreshold.size() > 1) classThreshold.remove(1);
																		classThreshold.add(1, helper);

																	}catch(Exception e) {
																	
																		ErrorMessage.display("Invalid Threshold 2 for is_God_Class.");
																	
																	}

																}
																
															}															
													
														});														
														
													}
												
												});
											
											}	
										
										});

									}
									
									if(!newValue) {
										
										TextField threshold2 = threshold1;
										
										try {

											int helper = Integer.parseInt(threshold2.getText());
											if(classThreshold.size() > 0) classThreshold.remove(0);
											classThreshold.add(0,helper);

										}catch(Exception e) {
										
											ErrorMessage.display("Invalid Threshold 1 for is_God_Class.");
										
										}

									}

								}
							
							});
						
							
							
						}
						
					});
					
					ComboBox smell2 = new ComboBox(FXCollections.observableArrayList(smells2));
					
					smell2.setPromptText("Choose Last Code Smell");
					
					grid.add(smell2, 0, 4);
					
					smell2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
							
							ComboBox metricsHelper = new ComboBox(FXCollections.observableArrayList(methodsMetricHelper));
							
							metricsHelper.setPromptText("Choose a Metric");
							
							grid.add(metricsHelper,0,5);
							
							metricsHelper.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

								@Override
								public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
									
									String[] methodsMetrics = methodsMetricHelper;
									
									for(int i = 0; i < methodsMetrics.length; i++) {
								
										if(newValue.toString().equals(methodsMetrics[i])) {
											
											if(methodOrder.size() > 0) methodOrder.remove(0);
											methodOrder.add(0, newValue.toString());
											methodsMetrics = remove(methodsMetrics, methodsMetrics[i]);
											break;
											
										}
										
									}
									
									final String[] methodsMetrics2 = methodsMetrics;
									final TextField threshold1 = new TextField();
									threshold1.setPromptText("Threshold");
									grid.add(threshold1, 1, 5);
									
									threshold1.focusedProperty().addListener(new ChangeListener<Boolean>() {

										@Override
										public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
											
											if(newValue){
												
												ComboBox logic = new ComboBox(FXCollections.observableArrayList(new String[]{"AND", "OR"}));

												logic.setPromptText("Choose a logic operator if you'd like");
												
												grid.add(logic, 2, 5);
												
												logic.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
													
													@Override
													public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

														final String[] methodsMetrics = methodsMetrics2;
														
														if(newValue.toString().equals("AND")) {

															if(methodLogic.size() > 0) methodLogic.remove(0);
															methodLogic.add(0, "AND");

														}else if(newValue.toString().equals("OR")) {

															if(methodLogic.size() > 0) methodLogic.remove(0);
															methodLogic.add(0, "OR");

														}
														
														ComboBox metric2 = new ComboBox(FXCollections.observableArrayList(methodsMetrics));
														
														metric2.setPromptText("Choose Last Metric");
														
														grid.add(metric2, 3, 5);
														
														metric2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

															@Override
															public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
																
																if(methodOrder.size() > 1) methodOrder.remove(1);
																methodOrder.add(1, methodsMetrics[0]);
																
																final TextField threshold2 = new TextField();
																threshold1.setPromptText("Threshold2");
																grid.add(threshold2, 4, 5);
																
																threshold2.focusedProperty().addListener(new ChangeListener<Boolean>() {

																	@Override
																	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																		
																		if(!newValue) {
																			
																			TextField threshold = threshold2;
																			
																			try {

																				int helper = Integer.parseInt(threshold.getText());
																				if(methodThreshold.size() > 1) methodThreshold.remove(1);
																				methodThreshold.add(1, helper);

																			}catch(Exception e) {
																			
																				ErrorMessage.display("Invalid Threshold 2 for is_Long_Method.");
																			
																			}

																		}
																		
																	}
																	
																});
																																
															}
														
														});
													}
												});
											}
														
											
											if(!newValue) {
												
												TextField threshold2 = threshold1;
												
												try {

													int helper = Integer.parseInt(threshold2.getText());
													if(methodThreshold.size() > 0) methodThreshold.remove(0);
													methodThreshold.add(0, helper);

												}catch(Exception e) {
												
													ErrorMessage.display("Invalid Threshold 1 for is_Long_Method.");
												
												}

											}
											
											
										}
										
									});
									
								}
								
							});
						}
					
					});
					
				
				}else if(smell.getValue().toString().equals("is_Long_Method")) {

					String[] smells = smellsHelper;
					String[] smells2 = remove(smells, "is_Long_Method");

					ComboBox smell2 = new ComboBox(FXCollections.observableArrayList(smells2));

					smell2.setPromptText("Choose the last Smell");
					
					grid.add(smell2, 0, 3);

					smell2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

							final String[] classMetricsHelper2 = classMetricsHelper;
							final GridPane grid = gridHelper;

							if(newValue.toString().equals("is_God_Class")) {

								String[] smells = smellsHelper;
								smells = remove(smells, "is_God_Class");
								final String[] smells2 = smells;

								final ComboBox metricsHelper = new ComboBox(FXCollections.observableArrayList(classMetricsHelper2));

								metricsHelper.setPromptText("Choose a Metric");
								
								grid.add(metricsHelper, 0, 4);

								metricsHelper.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

									@Override
									public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

										String[] classMetrics = classMetricsHelper2;
										ComboBox metrics = metricsHelper;

										if(newValue.equals("LOC_Class")) {

											if(classOrder.size() > 0) classOrder.remove(0);
											classOrder.add(0, "LOC_Class");
											classMetrics = remove(classMetrics,"LOC_Class");

										}else if(newValue.equals("NOM_Class")) {

											if(classOrder.size() > 0) classOrder.remove(0);
											classOrder.add(0, "NOM_Class");
											classMetrics = remove(classMetrics,"NOM_Class");	

										}else if(newValue.equals("WMC_Class")) {

											if(classOrder.size() > 0) classOrder.remove(0);
											classOrder.add(0, "WMC_Class");
											classMetrics = remove(classMetrics,"WMC_Class");

										}

										final TextField threshold1 = new TextField();
										threshold1.setPromptText("Threshold");
										grid.add(threshold1, 1, 4);
										final String[] classMetricsHelper = classMetrics;

										threshold1.focusedProperty().addListener(new ChangeListener<Boolean>() {

											@Override
											public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

												if(newValue){

													ComboBox logic = new ComboBox(FXCollections.observableArrayList(new String[]{"AND", "OR"}));

													logic.setPromptText("Choose a Logic operator if you'd like");
													
													grid.add(logic, 2, 4);

													logic.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

														String[] classMetrics = classMetricsHelper;

														@Override
														public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

															if(newValue.toString().equals("AND")) {

																if(classLogic.size() > 0) classLogic.remove(0);
																classLogic.add(0, "AND");

															}else if(newValue.toString().equals("OR")) {

																if(classLogic.size() > 0) classLogic.remove(0);
																classLogic.add(0, "OR");
													
															}

															ComboBox metric2 = new ComboBox(FXCollections.observableArrayList(classMetrics));

															metric2.setPromptText("Choose the second metric");
															
															grid.add(metric2, 3, 4);

															metric2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

																@Override
																public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

																	for(int i = 0; i < classMetrics.length; i++) {

																		if(newValue.toString().equals(classMetrics[i])) {

																			if(classOrder.size() > 1) classOrder.remove(1);
																			classOrder.add(1, newValue.toString());
																			classMetrics = remove(classMetrics, classMetrics[i]);
																			break;

																		}

																	}

																	TextField threshold2 = new TextField();
																	threshold2.setPromptText("Threshold2");
																	grid.add(threshold2, 4, 4);
																	final TextField threshold = threshold2;
																	final String[] classMetricsHelper = classMetrics;

																	threshold2.focusedProperty().addListener(new ChangeListener<Boolean>() {

																		@Override
																		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

																			if(newValue){

																				ComboBox logic = new ComboBox(FXCollections.observableArrayList(new String[]{"AND", "OR"}));

																				logic.setPromptText("Choose a Logic operator if you'd like");
																				
																				grid.add(logic, 5, 4);

																				logic.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

																					String[] classMetrics = classMetricsHelper;

																					@Override
																					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

																						if(newValue.toString().equals("AND")) {

																							if(classLogic.size() > 1) classLogic.remove(1);
																							classLogic.add(1, "AND");

																						}else if(newValue.toString().equals("OR")) {

																							if(classLogic.size() > 1) classLogic.remove(1);
																							classLogic.add(1, "OR");

																						}

																						ComboBox metric3 = new ComboBox(FXCollections.observableArrayList(classMetrics));
																						
																						metric3.setPromptText("Choose Last Metric");
																						
																						grid.add(metric3, 6, 4);

																						metric3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

																							@Override
																							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

																								if(newValue.toString().equals(classMetrics[0])) {

																									if(classOrder.size() > 2) classOrder.remove(2);
																									classOrder.add(2, newValue.toString());
																									classMetrics = remove(classMetrics, newValue.toString());

																								}

																								final TextField threshold3 = new TextField();
																								threshold3.setPromptText("Threshold3");
																								grid.add(threshold3, 7, 4);

																								threshold3.focusedProperty().addListener(new ChangeListener<Boolean>() {

																									@Override
																									public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

																										if(!newValue) {

																											TextField threshold = threshold3;

																											try {

																												int helper = Integer.parseInt(threshold.getText());
																												if(classThreshold.size() > 2) classThreshold.remove(2);
																												classThreshold.add(2, helper);

																											}catch(Exception e) {

																												ErrorMessage.display("Invalid Threshold 3  for is_God_Class.");

																											}

																										}

																									}																						

																								});

																							}

																						});

																					}

																				});
																			}

																			if(!newValue) {

																				TextField threshold2 = threshold;

																				try {

																					int helper = Integer.parseInt(threshold2.getText());
																					if(classThreshold.size() > 1) classThreshold.remove(1);
																					classThreshold.add(1, helper);

																				}catch(Exception e) {

																					ErrorMessage.display("Invalid Threshold 2 for is_God_Class.");

																				}

																			}

																		}															

																	});														

																}

															});

														}	

													});

												}

												if(!newValue) {

													TextField threshold2 = threshold1;

													try {

														int helper = Integer.parseInt(threshold2.getText());
														if(classThreshold.size() > 0) classThreshold.remove(0); 
														classThreshold.add(0, helper);

													}catch(Exception e) {

														ErrorMessage.display("Invalid Threshold 1 for is_God_Class.");

													}

												}

											}

										});
									}
									
								});
								
							}
							
						}
					
					});

					ComboBox metricsHelper = new ComboBox(FXCollections.observableArrayList(methodsMetricHelper));

					metricsHelper.setPromptText("Choose a metric");
					
					grid.add(metricsHelper,0,1);

					metricsHelper.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

							String[] methodsMetrics = methodsMetricHelper;

							for(int i = 0; i < methodsMetrics.length; i++) {

								if(newValue.toString().equals(methodsMetrics[i])) {

									if(methodOrder.size() > 0) methodOrder.remove(0);
									methodOrder.add(0, newValue.toString());
									methodsMetrics = remove(methodsMetrics, methodsMetrics[i]);
									break;

								}

							}

							final String[] methodsMetrics2 = methodsMetrics;
							final TextField threshold1 = new TextField();
							threshold1.setPromptText("Threshold");
							grid.add(threshold1, 1, 1);

							threshold1.focusedProperty().addListener(new ChangeListener<Boolean>() {

								@Override
								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

									if(newValue){

										ComboBox logic = new ComboBox(FXCollections.observableArrayList(new String[]{"AND", "OR"}));

										logic.setPromptText("Choose a Logic operator if you'd like");
										
										grid.add(logic, 2, 1);

										logic.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

											@Override
											public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

												final String[] methodsMetrics = methodsMetrics2;

												if(newValue.toString().equals("AND")) {

													if(methodLogic.size() > 0) methodLogic.remove(0);
													methodLogic.add(0, "AND");

												}else if(newValue.toString().equals("OR")) {

													if(methodLogic.size() > 0) methodLogic.remove(0);
													methodLogic.add(0, "OR");

												}

												ComboBox metric2 = new ComboBox(FXCollections.observableArrayList(methodsMetrics));

												metric2.setPromptText("Choose the last metric");
												
												grid.add(metric2, 3, 1);

												metric2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

													@Override
													public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

														if(methodOrder.size() > 1) methodOrder.remove(1);
														methodOrder.add(1, methodsMetrics[0]);

														final TextField threshold2 = new TextField();
														threshold1.setPromptText("Threshold2");
														grid.add(threshold2, 4, 1);

														threshold2.focusedProperty().addListener(new ChangeListener<Boolean>() {

															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

																if(!newValue) {

																	TextField threshold = threshold2;

																	try {

																		int helper = Integer.parseInt(threshold.getText());
																		if(methodThreshold.size() > 1) methodThreshold.remove(1);
																		methodThreshold.add(1, helper);

																	}catch(Exception e) {

																		ErrorMessage.display("Invalid Threshold 2 for is_Long_Method.");

																	}

																}

															}

														});

													}

												});
											}
										});
									}


									if(!newValue) {

										TextField threshold2 = threshold1;

										try {

											int helper = Integer.parseInt(threshold2.getText());
											if(methodThreshold.size() > 0) methodThreshold.remove(0);
											methodThreshold.add(0, helper);

										}catch(Exception e) {

											ErrorMessage.display("Invalid Threshold 1 for is_Long_Method.");

										}

									}


								}

							});

						}

					});
						
				}
				
				Button test = new Button("Test Rules");
				grid.add(test, 0, 6);
				
				final Text t1 = new Text();
				t1.setWrappingWidth(250);
		    	grid.add(t1, 0, 10);
		    	
				test.setOnAction(new EventHandler<ActionEvent>() {
					
				    @Override 
				    public void handle(ActionEvent e) {

				    	new Metrics().getMetrics(".\\bin\\src\\main\\java\\ES_2Sem_2021_Grupo53\\ES_2Sem_2021_Grupo53\\Dummy\\jasml_0.10\\src", methodOrder, methodLogic, methodThreshold, classOrder, classLogic, classThreshold);
				    	int[] numbers = new Metrics().compare(new File(".\\Code_Smells.xlsx"), new File(".\\src_metrics.xlsx"));
				    	
				    	String s = CalibratePopUp.display(numbers);
				    	
				    	if(s.equals("MainMenu")) {

				    		primaryStage.close();
				    		new Metrics().writeOnMetricsFile(methodOrder, methodLogic, methodThreshold, classOrder, classLogic, classThreshold);
				    		MainMenu.display(methodOrder, methodLogic, methodThreshold, classOrder, classLogic, classThreshold);
				    	
				    	}
				    	
				    	Text t = t1;
				    	
				    	t.setText(s);
				    	
				    	
				    }
				    
				});
				
				
			}});
		
		try {
			
			File f = new File("allMetricsFile.txt");
			
			String [] demzTheRules = new String[0];	
		
			Scanner myReader = new Scanner(f);
			 
			 while (myReader.hasNextLine()) {
				 
		        String line = myReader.nextLine();			 			 
				demzTheRules = add(demzTheRules, line);
	
		    }
			 
		    myReader.close();
			
			ComboBox rules = new ComboBox(FXCollections.observableArrayList(demzTheRules));
			
			rules.setPromptText("Choose Old Rule");
			
			gridHelper.add(rules, 0, 11);
			
			rules.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(!newValue.equals("")) {
						Button submit2 = new Button("Use Old Rule");
						gridHelper.add(submit2, 0, 12);

						submit2.setOnAction( e-> {

							String[] arrays = rules.getValue().toString().split(";");

							methodOrder = new ArrayList<String>();
							methodLogic = new ArrayList<String>();
							methodThreshold = new ArrayList<Integer>();
							classOrder = new ArrayList<String>();
							classLogic = new ArrayList<String>();
							classThreshold = new ArrayList<Integer>();

							String[] methodOrderFromFile = arrays[0].split(",");
							String[] methodLogicFromFile = arrays[1].split(",");
							String[] methodThresholdsFromFile = arrays[2].split(",");
							String[] classOrderFromFile = arrays[3].split(",");
							String[] classLogicFromFile = arrays[4].split(",");
							String[] classThresholdsFromFile = arrays[5].split(",");


							for(int i = 0; i < methodOrderFromFile.length; i++) methodOrder.add(methodOrderFromFile[i].replace("[", "").replace("]", ""));

							if(methodLogicFromFile.length > 0) methodLogic.add(methodLogicFromFile[0]);

							for(int i = 0; i < methodThresholdsFromFile.length; i++) methodThreshold.add(Integer.parseInt(methodThresholdsFromFile[i].replace("[", "").replace("]", "").trim()));

							for(int i = 0; i < classOrderFromFile.length; i++) classOrder.add(classOrderFromFile[i].replace("[", "").replace("]", ""));

							for(int i = 0; i < classLogicFromFile.length; i++) classLogic.add(classLogicFromFile[i].replace("[", "").replace("]", ""));

							for(int i = 0; i < classThresholdsFromFile.length; i++) classThreshold.add(Integer.parseInt(classThresholdsFromFile[i].replace("[", "").replace("]", "").trim()));

							primaryStage.close();
							MainMenu.display(methodOrder, methodLogic, methodThreshold, classOrder, classLogic, classThreshold);

						});
					
				}

			}
		
		});
			
		}catch(Exception e){
		
			ErrorMessage.display("No saved Rules at present.");
			
		}
		
		Scene scene = new Scene(gridHelper, 1000, 500);
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
		
		return ;
		
	}
		
}
