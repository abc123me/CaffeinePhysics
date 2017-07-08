package net.net16.jeremiahlowe.caffeinephysics;

import net.net16.jeremiahlowe.caffeinephysics.collider.Collider;

public class LayerMask {
	private boolean[] layers;
	private final int layerAmount;
	
	public LayerMask(int layerAmount, boolean allowAll){
		layers = new boolean[layerAmount];
		setAll(allowAll);
		this.layerAmount = layerAmount;
	}
	
	public void setLayer(int layer, boolean allow){
		checkLayerID(layer);
		layers[layer] = allow;
	}
	public boolean getLayer(int layer){
		checkLayerID(layer);
		return layers[layer];
	}
	public void setAll(boolean allow){
		for(int i = 0; i < layerAmount; i++) layers[i] = allow;
	}
	private void checkLayerID(int layer){
		if(layer < 0 || layer >= layerAmount) 
			throw new RuntimeException("Layer " + layer + "is invalid!");
	}

	public boolean tryLayer(int layer) {
		if(layer < 0 || layer >= layerAmount) return false;
		return getLayer(layer);
	}
}
