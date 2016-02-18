package controls;

public class Utility {
    
    
    public int[][][] processImg(int[][][] threeDPix,
                              int imgRows,
                              int imgCols){

   
    int[][][] output = new int[imgRows][imgCols][4];

    double[][][] working3D = copyToDouble(threeDPix);
    
    for(int row = 0;row < imgRows;row++){
      for(int col = 0;col < imgCols;col++){

      }//end inner loop
    }//end outer loop
    
    
    double[][] redPlane = extractPlane(working3D,1);
    processPlane(redPlane);
    
    insertPlane(working3D,redPlane,1);
    
   
    double[][] greenPlane = extractPlane(working3D,2);
    processPlane(greenPlane);
    
    insertPlane(working3D,greenPlane,2);
    
    
    double[][] bluePlane = extractPlane(working3D,3);
    processPlane(bluePlane);
  
    insertPlane(working3D,bluePlane,3);

    output = copyToInt(working3D);
   
    return output;

  }
  
  double[] extractRow(double[][] colorPlane,int row){
    int numCols = colorPlane[0].length;
    double[] output = new double[numCols];
    for(int col = 0;col < numCols;col++){
      output[col] = colorPlane[row][col];
    }
    return output;
  }
  void insertRow(double[][] colorPlane,
                 double[] theRow,
                 int row){
    int numCols = colorPlane[0].length;
    double[] output = new double[numCols];
    for(int col = 0;col < numCols;col++){
      colorPlane[row][col] = theRow[col];
    }//end outer loop
  }//end insertRow
  
  double[] extractCol(double[][] colorPlane,int col){
    int numRows = colorPlane.length;
    double[] output = new double[numRows];
    for(int row = 0;row < numRows;row++){
      output[row] = colorPlane[row][col];
    }//end outer loop
    return output;
  }
  void insertCol(double[][] colorPlane,
                 double[] theCol,
                 int col){
    int numRows = colorPlane.length;
    double[] output = new double[numRows];
    for(int row = 0;row < numRows;row++){
      colorPlane[row][col] = theCol[row];
    }//end outer loop
  }//end insertCol
  
  public double[][] extractPlane(
                              double[][][] threeDPixDouble,
                              int plane){
    
    int numImgRows = threeDPixDouble.length;
    int numImgCols = threeDPixDouble[0].length;
    
    
    double[][] output =new double[numImgRows][numImgCols];

    
    for(int row = 0;row < numImgRows;row++){
      for(int col = 0;col < numImgCols;col++){
        output[row][col] =
                          threeDPixDouble[row][col][plane];
      }//end loop on col
    }//end loop on row
    return output;
  }//end extractPlane
 
  public void insertPlane(
                              double[][][] threeDPixDouble,
                              double[][] colorPlane,
                              int plane){
    
    int numImgRows = threeDPixDouble.length;
    int numImgCols = threeDPixDouble[0].length;
    
    
    for(int row = 0;row < numImgRows;row++){
      for(int col = 0;col < numImgCols;col++){
        threeDPixDouble[row][col][plane] = 
                                      colorPlane[row][col];
      }//end loop on col
    }//end loop on row
  }//end insertPlane
 
  double[][][] copyToDouble(int[][][] threeDPix){
    int imgRows = threeDPix.length;
    int imgCols = threeDPix[0].length;
    
    double[][][] new3D = new double[imgRows][imgCols][4];
    for(int row = 0;row < imgRows;row++){
      for(int col = 0;col < imgCols;col++){
        new3D[row][col][0] = threeDPix[row][col][0];
        new3D[row][col][1] = threeDPix[row][col][1];
        new3D[row][col][2] = threeDPix[row][col][2];
        new3D[row][col][3] = threeDPix[row][col][3];
      }//end inner loop
    }//end outer loop
    return new3D;
  }
  int[][][] copyToInt(double[][][] threeDPixDouble){
    int imgRows = threeDPixDouble.length;
    int imgCols = threeDPixDouble[0].length;
    
    int[][][] new3D = new int[imgRows][imgCols][4];
    for(int row = 0;row < imgRows;row++){
      for(int col = 0;col < imgCols;col++){
        new3D[row][col][0] = 
                         (int)threeDPixDouble[row][col][0];
        new3D[row][col][1] = 
                         (int)threeDPixDouble[row][col][1];
        new3D[row][col][2] = 
                         (int)threeDPixDouble[row][col][2];
        new3D[row][col][3] = 
                         (int)threeDPixDouble[row][col][3];
      }//end inner loop
    }//end outer loop
    return new3D;
  }//end copyToInt
 
  void clipToZero(double[][] colorPlane){
    int numImgRows = colorPlane.length;
    int numImgCols = colorPlane[0].length;
    //Do the clip
    for(int row = 0;row < numImgRows;row++){
      for(int col = 0;col < numImgCols;col++){
        if(colorPlane[row][col] < 0){
          colorPlane[row][col] = 0;
        }//end if
      }//end inner loop
    }//end outer loop
  }//end clipToZero
 
  void clipTo255(double[][] colorPlane){
    int numImgRows = colorPlane.length;
    int numImgCols = colorPlane[0].length;
    //Do the clip
    for(int row = 0;row < numImgRows;row++){
      for(int col = 0;col < numImgCols;col++){
        if(colorPlane[row][col] > 255){
          colorPlane[row][col] = 255;
        }//end if
      }//end inner loop
    }//end outer loop
  }//end clipTo255
  
  void processPlane(double[][] colorPlane){
    
    int imgRows = colorPlane.length;
    int imgCols = colorPlane[0].length;
    
    
    for(int row = 0;row < imgRows;row++){
      double[] theRow = extractRow(colorPlane,row);
      
      double[] theXform = new double[theRow.length];
      //ForwardDCT01.transform(theRow,theXform);
      
      
      insertRow(colorPlane,theXform,row);
    }//end for loop
    
   
    for(int col = 0;col < imgCols;col++){
      double[] theCol = extractCol(colorPlane,col);

      double[] theXform = new double[theCol.length];
      //ForwardDCT01.transform(theCol,theXform);

      insertCol(colorPlane,theXform,col);
    }//end for loop
   
   
    double max = getMax(colorPlane);
    System.out.println(max);
    requanToElevenBits(colorPlane,max/1023);
    
    System.out.println(getMax(colorPlane));
    
    restoreSpectralMagnitude(colorPlane,max/1023);
    
    System.out.println(getMax(colorPlane));
    
    
    for(int col = 0;col < imgCols;col++){
      double[] theXform = extractCol(colorPlane,col);
      
      double[] theCol = new double[theXform.length];
    
      //InverseDCT01.transform(theXform,theCol);
      
    
      insertCol(colorPlane,theCol,col);
    }//end for loop

    for(int row = 0;row < imgRows;row++){
      double[] theXform = extractRow(colorPlane,row);
      
      double[] theRow = new double[theXform.length];
      //Now transform it back
      //InverseDCT01.transform(theXform,theRow);
      
      //Insert it back in
      insertRow(colorPlane,theRow,row);
    }//end for loop
    clipToZero(colorPlane);
    clipTo255(colorPlane);

  }//end processPlane
  double getMax(double[][] plane){
    int rows = plane.length;
    int cols = plane[0].length;
    double max = Double.MIN_VALUE;
    for(int row = 0;row < rows;row++){
      for(int col = 0;col < cols;col++){
        double value = plane[row][col];
        if(value < 0){
          value = -value;
        }//end if
        if(value > max){
          max = value;
        }//end if
      }//end inner loop
    }//end outer loop
    return max;
  }//end getMax

  void requanToElevenBits(double[][] plane,double divisor){
    int rows = plane.length;
    int cols = plane[0].length;
    for(int row = 0;row < rows;row++){
      for(int col = 0;col < cols;col++){
        plane[row][col] = round(plane[row][col]/divisor);
      }//end inner loop
    }//end outer loop
  }//end requanToElevenBits

  void restoreSpectralMagnitude(
                           double[][] plane,double factor){
    int rows = plane.length;
    int cols = plane[0].length;
    for(int row = 0;row < rows;row++){
      for(int col = 0;col < cols;col++){
        plane[row][col] = factor * plane[row][col];
      }//end inner loop
    }//end outer loop
  }//end restoreSpectralMagnitude

    private double log10(double d) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private double round(double d) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
  
}
