/** 
 *  Game of Life.
 */

 public class GameOfLife {

	public static void main(String[] args) {
		String fileName = args [0];
		 play(fileName);

	}
	
	// Reads the data file and prints the initial board.
	public static void test1(String fileName) {
		int[][] board = read(fileName);
		//print(board);
	}
		
	// Reads the data file, and runs a test that checks 
	// the count and cellValue functions.
	public static void test2(String fileName) {
		int[][] board = read(fileName);
		System.out.print(cellValue(board, 2, 3));

	}
		
	// Reads the data file, plays the game for Ngen generations, 
	// and prints the board at the beginning of each generation.
	public static void test3(String fileName, int Ngen) {
		int[][] board = read(fileName);
		for (int gen = 0; gen < Ngen; gen++) {
			System.out.println("Generation " + gen + ":");
			print(board);
			board = evolve(board);
		}
	}
		
	// Reads the data file and plays the game, for ever.
	public static void play(String fileName) {
		int[][] board = read(fileName);
		while (true) {
			show(board);
			board = evolve(board);
		}
	}
	
	// Reads the initial board configuration from the file whose name is fileName, uses the data
	// to construct and populate a 2D array that represents the game board, and returns this array.
	// Live and dead cells are represented by 1 and 0, respectively. 
	public static int[][] read(String fileName) {
		In in = new In(fileName); // Constructs an In object for reading the input file
		int rows = Integer.parseInt(in.readLine());
		int cols = Integer.parseInt(in.readLine());
		int[][] board = new int[rows + 2][cols + 2];
		int counter = 1;
		while (in.hasNextLine()) {
		String x = in.readLine(); 
		for(int i=0; i< x.length() ; i++){
				if(x.charAt(i) == 'x'){
				board[counter][i+1]=1;
				}
			  }
			  counter++;
		}
		return board;
	}
	
	// Creates a new board from the given board, using the rules of the game.
	// Uses the cellValue(board,i,j) function to compute the value of each 
	// cell in the new board. Returns the new board.
	public static int[][] evolve(int[][] board) {
		int n= board.length;
		int m= board[0].length;
		int[][] newboard= new int[n][m];

		for(int i=1 ; i<n-1; i++){
			for(int j=1 ; j<m-1; j++){
				newboard[i][j] = cellValue(board, i, j);
			}
		}
		return newboard;
	}

	// Returns the value that cell (i,j) should have in the next generation.
	// Uses the count(board,i,j) function to count the number of alive neighbors.
	public static int cellValue(int[][] board, int i, int j) {
		int livingNeighbors  = count(board, i, j);
		boolean alive = false ;
	
		if ( board[i][j]==1 ){
			alive = true ; 
		}

		if ( alive ){
			if ( livingNeighbors < 2 || livingNeighbors > 3 ){
				return 0 ; 
			}
			else {
				return 1 ;
			}
		}

		else if ( livingNeighbors == 3 ){
				return 1 ; 
			}
			else {
				return 0 ;
			}
	}
	
	// Counts and returns the number of living neighbors of the given cell
	public static int count(int[][] board, int i, int j) {
		int livingNeighbors = 0;
		for ( int x = -1 ; x <= 1 ; x++ ) {
			for (int y = -1; y <= 1; y++) {
				boolean cell = ( x == 0 && y == 0 );
				if ((!cell) && (i+x>=0) && (i+x<board.length) && (j+y>=0) && (j+y<board[0].length) ) {
					if ( board[i + x][j + y] == 1 ) {
						livingNeighbors++ ;
					}
				}
			}
		}
		
		return livingNeighbors;
	}
	
	// Prints the board. Alive and dead cells are printed as 1 and 0, respectively.
    public static void print(int[][] arr) {
		for( int i=1; i < arr.length -1 ; i++){
			for( int j=1 ; j <arr[0].length -1 ; j++){
				System.out.printf("  %d" , arr[i][j] );
			
			}
			System.out.printf("%n");
		}
	}
		
    // Displays the board. Living and dead cells are represented by black and white squares, respectively.
    // We use a fixed-size canvas of 900 pixels by 900 pixels for displaying game boards of different sizes.
    // In order to handle any given board size, we scale the X and Y dimensions according to the board size.
    // This results in the following visual effect: The smaller the board, the larger the squares
	// representing cells.
	public static void show(int[][] board) {
		StdDraw.setCanvasSize(900, 900);
		int rows = board.length;
		int cols = board[0].length;
		StdDraw.setXscale(0, cols);
		StdDraw.setYscale(0, rows);

		// Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
		
		// For each cell (i,j), draws a filled square of size 1 by 1 (remember that the canvas was 
		// already scaled to the dimensions rows by cols, which were read from the data file). 
		// Uses i and j to calculate the (x,y) location of the square's center, i.e. where it
		// will be drawn in the overall canvas. If the cell contains 1, sets the square's color
		// to black; otherwise, sets it to white. In the RGB (Red-Green-Blue) color scheme used by
		// StdDraw, the RGB codes of black and white are, respetively, (0,0,0) and (255,255,255).
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int color = 255 * (1 - board[i][j]);
				StdDraw.setPenColor(color, color, color);
				StdDraw.filledRectangle(j + 0.5, rows - i - 0.5, 0.5, 0.5);
			}
		}
		StdDraw.show();
		StdDraw.pause(100); 
	}

}