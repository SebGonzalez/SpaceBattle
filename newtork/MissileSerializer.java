package newtork;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import client.Model.Missile;

public class MissileSerializer extends Serializer<Missile>{

	@Override
	public Missile read(Kryo arg0, Input input, Class<Missile> arg2) {
		Missile m = new Missile(input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat());
		return m;
	}

	@Override
	public void write(Kryo arg0, Output output, Missile missile) {
		output.writeFloat(missile.getX());
		output.writeFloat(missile.getY());
		output.writeFloat(missile.getDirectionX());
		output.writeFloat(missile.getDirectionY());
	}

}
