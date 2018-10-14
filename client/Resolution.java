package client;

public enum Resolution{
		LOW(960, 540),
		MED(1280, 720),
		HIGH(1600, 900);
		
		
		private final int x;
		private final int y;
		
		private Resolution(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
}

