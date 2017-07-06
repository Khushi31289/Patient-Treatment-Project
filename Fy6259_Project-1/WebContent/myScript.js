var score = 0;
var arrayOfColors = ["Violet", "FireBrick", "Indigo"  , "Brown" ,"LimeGreen", "Yellow", "Orange", "Red"]; 
var colCount = 15, rowCount = 30;
var level = 1;
var canvasFrame = [];
var gameOver, Xpos, Ypos, interval, thisOject;
var milliSec = 25;
var shapes; 

var canvas = null; 
var ctx = null; 
var levelOfDifficulty = 270; // for beginner
var width = 450, height = 600;
var shapeWidth , shapeHeight ;



var restartMsg = "Do you really want to restart the Game?";
var lostGameMsg = "Oops, you lost... :(";


//Function to generate random color
function getColor(){
	return ("#"+((1<<24)*Math.random()|0).toString(16));
}

function geNewObjectID(){
	var obj = Math.floor( Math.random() * shapes.length );
	return(obj);
}


$( document ).ready(function() {
	canvas = $( '#myCanvas' );
	if(checkValue(canvas)){
		ctx = $( '#myCanvas' ).get(0).getContext( '2d' );
		shapeWidth = width  / colCount; 
		shapeHeight = height / rowCount;
	}
	$('#selLevel').val(1);
	$('#divScore').addClass('hideClass');
});




function changeLevelOfDiffculty(){
	var level = parseInt($('#selLevel').val());
	$( '#myCanvas' ).focus();
	switch(level ){
		case 1:
			levelOfDifficulty  = 270;
			score = 0;
			startGame();
			$( '#myCanvas' ).focus();
		break;
		case 2:
			levelOfDifficulty  = 180;
			score = 0;
			startGame();
			$( '#myCanvas' ).focus();
		break;
		case 3:
			levelOfDifficulty  = 90;
			score = 0;
			startGame();
			$( '#myCanvas' ).focus();
		break;
	}
	
}


function checkValue(tempVal){
	if( typeof(tempVal) != 'undefined' && tempVal != null ){
		return true;
	}else{
		return false;
	}
}

//The standard dimension used are 4 X 4 
//so if the object rotates then it wont be so visible


function newShape() {
    var id = Math.floor(getNewObjectID()); //Math.floor( Math.random() * shapes.length );//getNewObjectID();
    var shape = shapes[ id ]; // maintain id for color filling
    thisOject = [];
    for ( var y = 0; y < 4; ++y ) {
        thisOject[ y ] = [];
        for ( var x = 0; x < 4; ++x ) {
            var i = 4 * y + x;

            if ( typeof shape[ i ] != 'undefined' && shape[ i ] ) {
                thisOject[ y ][ x ] = id + 1;
            }
            else {
                thisOject[ y ][ x ] = 0;
            }
        }
    }
    // When new object let it appear in the middle
    Xpos = 6;
    Ypos = 0;
}

//For every new object appearing give a unique ID
function getNewObjectID(){
	if(checkValue(shapes)){
		return Math.random() * shapes.length; 
	}
	return 1;
}


// clears the canvasFrame
function flushCanvas() {
	var i = 0, j =0;
    for ( i = 0; i < rowCount; i++ ) {
        canvasFrame[ i ] = [];
        for ( j = 0; j < colCount; j++ ) {
            canvasFrame[ i ][ j ] = 0;
        }
    }
}

// keep the element moving down, creating new shapes and clearing lines
function moveObjectsDown() {
    if ( isObjectAcceptable( 0, 1 ) ) {
        ++Ypos;
    }
    // if the element settled
    else {
        stopObject();
        clearLines();
        if (gameOver) {
        	clearInterval(interval);
        	alert("Game over..! :(");    	    
            return false;
        }
        newShape();
    }
}

// Pause the object & lay it so it can sit onto the canvasFrame
function stopObject() {
	var i = 0, j= 0;
    for ( i = 0; i < 4; i++ ) {
        for ( j = 0; j < 4; j++ ) {
            if ( thisOject[ i ][ j ] ) {
                canvasFrame[ i + Ypos ][ j + Xpos ] = thisOject[ i ][ j ];
            }
        }
    }
}

// Rotate the falling block by rotating the contents of array i.e.circularly swapping the array cell values 
function rotate( thisOject ) {
    var thisObject = [];
    var i = 0, j =0;
    for ( i = 0; i < 4; i++ ) {
        thisObject[ i ] = [];
        for ( j = 0; j < 4; j++ ) {
            thisObject[ i ][ j ] = thisOject[ 3 - j ][ i ];
        }
    }
    return thisObject;
}

// check if any lines are filled and clear them
function clearLines() {
	var i = 0 , j = 0;
    for ( i = rowCount - 1; i >= 0; i-- ) {
        var rowFilled = true;
        for ( j = 0; j < colCount; j++ ) {
            if ( canvasFrame[ i ][ j ] == 0 ) {
                rowFilled = false;
                break;
            }
        }
        //checking if a row is filled then clear that row and increment the score
        var k = i;
        if ( rowFilled ) {
            for ( k = i; k > 0; k-- ) {
                for ( j = 0; j < colCount; j++ ) {
                    canvasFrame[ k ][ j ] = canvasFrame[ k - 1 ][ j ];
                    	//incrementing the score
                }
            }
            incrementScore();
            console.log(score);
            
            ++i;
        }
    }
}

function incrementScore(){
	switch(levelOfDifficulty ){
	case 270:
		score =score+100;
		$( '#myCanvas' ).focus();
	break;
	case 180:
		score =score + 200;
		$( '#myCanvas' ).focus();
	break;
	case 90:
		score =score + 300;
		$( '#myCanvas' ).focus();
	break;
	}
	$('#lblScore').text(score);
}

// checks if the resulting position of thisOject shape will be feasible
//Ensure that user can't rotate the object in such way that it would get stuck outside the canvas edges
function isObjectAcceptable( horizontalVal, verticalVal, thisObject ) {
    horizontalVal = horizontalVal || 0;
    verticalVal = verticalVal || 0;
    if(checkValue(horizontalVal ) && checkValue(verticalVal )){
	    horizontalVal = Xpos + horizontalVal;
	    verticalVal = Ypos + verticalVal;
    }
    thisObject = thisObject || thisOject;
    for ( var i = 0; i < 4; i++ ) {
        for ( var j = 0; j < 4; j++ ) {
            if ( thisObject[ i ][ j ] ) {
            	var iVVal = i + verticalVal ;
            	var jHVal = j + horizontalVal ;
            	var temp = false;
            	if ( typeof canvasFrame[ i + verticalVal ] == 'undefined'
                    || typeof canvasFrame[ i + verticalVal ][ j + horizontalVal ] == 'undefined'
                    || canvasFrame[ i + verticalVal ][ j + horizontalVal ] ) {
            		temp = true;
            	}
            		if( temp || jHVal < 0 || jHVal >= colCount 
                            || iVVal >= rowCount ){
                  	// gameover as user top of canvas cell and shape touched
                      if (verticalVal == 1){
                      	gameOver = true;   
                      }
                      return false;
                  }
            }
        }
    }
    return true;
}


function checkForUD(ele){
	if(typeof (ele) == 'undefined'){
		return true;
	}else{
		return false;
	}
}


function checkAndAssign(tempVal){
	if(tempVal){
		tempVal = tempVal;
	}else{
		tempVal = 0;
	}
}

function startGame() {
	//var ch = changeButtonText();
	if($('#divScore').hasClass('hideClass')){
		$('#divScore').removeClass('hideClass').addClass('blinkClass');
	}
	shapes =  [
    [ 1, 1, 1, 0, 1 ],
    [ 1, 1, 0, 0, 0, 1, 1 ],
    [ 1, 1, 0, 0, 1, 1 ],
    [ 1, 1, 1, 0, 0, 0, 1 ],
    [ 0, 1, 1, 0, 1, 1 ],
    [ 0, 1, 0, 0, 1, 1, 1 ],
     [ 1, 1, 1, 1 ]
];
	var choice = false;
	$( '#myCanvas' ).focus();
	var btnText = document.getElementById("btnStart").innerHTML; 
	//$("#btnStart span").val();//.attr('value','save');
	//console.log(btnText );
	if(checkValue(btnText )){
		if(btnText.toLowerCase() == "start game"){
			document.getElementById("btnStart").innerHTML = "Restart Game";
			choice= true;
			$( '#myCanvas' ).focus();
		}
		else{
			choice = confirm(restartMsg );
			$( '#myCanvas' ).focus();
		}
		if(choice == true){
			$( '#myCanvas' ).focus();
		    clearInterval(interval);
		    setInterval( makeMovingObjects, milliSec );
		    flushCanvas();
		    newShape();
		    gameOver = false;
		    interval = setInterval( moveObjectsDown, levelOfDifficulty );
		}
	}
}

/*function changeButtonText(){
	var btnText = document.getElementById("btnStart").innerHTML;
	console.log(btnText );
	if(btnText.toLowerCase() == "start game"){
		document.getElementById("btnStart").innerHTML = "Restart Game";
	}
	else{
		var choice = confirm(restartMsg );
		return choice;
	}
	return true;
}
*/

function processTheKey(event){
	//37= left, 38- up/turn, 39-right, 40- down
	var asciiValue = parseInt( event.keyCode);
	//alert(asciiValue  + 'inside');
	    if ( asciiValue != null){
	    	if(  asciiValue  != 'undefined' && 
	    			asciiValue >= 37 && asciiValue <= 40) {
	    		switch ( asciiValue ) {
	            case 37:
	                if ( isObjectAcceptable( -1 ) ) {
	                    Xpos--;
	                }
	                break;
	            case 38:
	                var rotated = rotate( thisOject );
	                if ( isObjectAcceptable( 0, 0, rotated ) ) {
	                    thisOject = rotated;
	                }
	                break;
	            case 39:
	                if ( isObjectAcceptable( 1 ) ) {
	                    Xpos++;
	                }
	                break;
	            case 40:
	                if ( isObjectAcceptable( 0, 1 ) ) {
	                    Ypos++;
	                }
	                break;

	        }
		        makeMovingObjects();
	    	}
	    }
}




// At location x,y on canvas create/make a new shape
function createShape( x, y ) {
	if(checkValue(x) && checkValue(y)){
		if(checkValue(ctx)){
			makeRectangle(x, y );
			makeStroke(x , y);
		}
	}
}

function makeRectangle( x, y){
	ctx.fillRect( shapeWidth * x, shapeHeight * y, 
					shapeWidth - 1 , shapeHeight - 1 );
}

 function makeStroke( x, y){
	 ctx.strokeRect( shapeWidth * x, shapeHeight * y, 
	 			shapeWidth - 1 , shapeHeight - 1 );
 }

/* Making the canvas and getting new objects
	Also, making them drift from top to bottom
*/ 
function makeMovingObjects() {
	if(checkValue(ctx)){
	    ctx.clearRect( 0, 0, width, height );
		//var newColor = getColor();
	    var i = 0 , j = 0;
	    for ( j = 0; j < colCount; j++ ) {
	        for ( i = 0; i < rowCount; i++ ) {
	        	//setObjectProperties(canvasFrame, j,i);
	        	var temp = canvasFrame[ i ][ j ];
	            if ( temp) {
	            	var newColor = arrayOfColors[ temp - 1 ];
	            	setContext(newColor, 'black' );
	                createShape( j, i );
	            }
	        }
	    }

	    for ( i = 0; i < 4; i++ ) {
	        for ( j = 0; j < 4; j++ ) {
	       // setObjectProperties(j,i);
	            if ( thisOject[ i ][ j ] ) {
	            	var newColor = arrayOfColors[ thisOject[ i ][ j ] - 1 ];
	            	setContext(newColor, newColor);
	                createShape( Xpos + j, Ypos + i );
	            }
	            
	        }
	    }
	}
}

function setContext(newColor , shadowColor){
    ctx.fillStyle = newColor;
	ctx.shadowBlur = 10;
	ctx.shadowColor = shadowColor;
}
