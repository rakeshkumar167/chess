/**
 * This software is copyright property of inkkpot.com
 * Any unauthorised usage is prohibited.
 * Please contact rakeshkumar167@gmail.com for permissions.
 */
package com.inkkpot.chess.move;

import com.inkkpot.chess.enums.Position;

/**
 * @author Rakesh
 *
 */
public class NormalMove {
	private Position from;
	private Position to;
	private int val;
	
	public NormalMove(Position from, Position to, int val){
		this.from=from;
		this.to=to;
		this.val=val;
	}
	
	public NormalMove(Position from, Position to){
		this.from=from;
		this.to=to;
	}
	
	public Position getFrom() {
		return from;
	}
	public void setFrom(Position from) {
		this.from = from;
	}
	public Position getTo() {
		return to;
	}
	public void setTo(Position to) {
		this.to = to;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
}
