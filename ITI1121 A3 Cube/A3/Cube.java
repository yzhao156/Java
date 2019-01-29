public class Cube{

	private Color a,b,c,d,e,f;
	private Color[] faces;
	private int orientations = 0;

	public Cube(Color[] faces){		
		a=faces[0];
		b=faces[1];
		c=faces[2];
		d=faces[3];
		e=faces[4];
		f=faces[5];
		this.faces=faces;
	}

	public Color getUp(){
		return faces[0];
	}

	public Color getFront(){
		return faces[1];
	}

	public Color getRight(){
		return faces[2];
	}

	public Color getBack(){
		return faces[3];
	}

	public Color getLeft(){
		return faces[4];
	}

	public Color getDown(){
		return faces[5];
	}

	public Color[] getFaces(){
		return faces;
	}

	public String toString(){
		String temp = "[";
		for (int i=0; i<5; i++){
			temp += (faces[i]+", ");
		}
		temp += (faces[5] + "]");
		return temp;
	}



	public boolean hasNext(){
		return orientations<24;
	}

	public void reset(){
		faces=new Color[]{a,b,c,d,e,f};
		orientations = 0;
	}

	private void Rotate(){
		Color tmp = faces[4];
		faces[4]=faces[3];
		faces[3]=faces[2];
		faces[2]=faces[1];
		faces[1]=tmp;
	}

	private void RightRoll(){
		Color tmp = faces[4];
		faces[4]=faces[5];
		faces[5]=faces[2];
		faces[2]=faces[0];
		faces[0]=tmp;
	}

	private void LeftRoll(){
		Color tmp = faces[0];
		faces[0]=faces[2];
		faces[2]=faces[5];
		faces[5]=faces[4];
		faces[4]=tmp;
	}

	public Color[] Identity(){
		faces=new Color[]{a,b,c,d,e,f};
		return faces;
	}

	public void next(){
		if(! hasNext()){
			throw new IllegalStateException("The next sets the orientation of the Cube to one that has been seen");
		}else{
			switch(orientations){
				case 0:
					Identity();
					orientations++;
					break;

				case 4:
				case 8:
				case 20:
					RightRoll();
					orientations++;
					break;

				case 12:
				case 16:
					LeftRoll();
					orientations++;
					break;

				default:
					Rotate();
					orientations++;
					break;
			}
		}
	}

	public Cube copy(){
		Color s = getUp();
		Color v = getFront();
		Color t = getRight();
		Color x = getBack();
		Color y = getLeft();
		Color z = getDown();
		Color[] nfaces= new Color[]{s,v,t,x,y,z};
		Cube ncube = new Cube(nfaces);
		return ncube;
	}

	public Cube(Cube other){
		if (other == null){
			throw new IllegalStateException("cube cannot be null");
		}
		this.a = other.a;
		this.b = other.b;
		this.c = other.c;
		this.d = other.d;
		this.e = other.e;
		this.f = other.f;
		faces = new Color[6];

		for (int i=0; i<6; i++){
			this.faces[i] = other.getFaces()[i];
		}
		
		this.orientations = other.orientations;
		
	}
}