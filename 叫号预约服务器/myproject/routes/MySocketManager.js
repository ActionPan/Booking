// JavaScript Document
//自定义webSocket管理类
	
module.exports=SocketManager;

var socket

//io是服务器的socket.io
function SocketManager(io)
{
	
	this.io=io;
	
	io.sockets.on('connection',function(socket){
		
	socket.emit('welcome',{text:'hello my friend!'});

	socket.on('message',function(data){
		 
		 console.log('server received:'+data);
		 
		 io.sockets.emit('user message',data);
		 
		 });
	
	socket.on('clientBroadcast',function(data){
		
		console.log('clientBroadcast:'+data);
		socket.broadcast.emit('serverBroadcast',data);
		
		});
	
    	
	});
	
	//自定义方法用以广播
	this.boradcast=function(req,res)
	{
		
		var CompanyName=req.body.CompanyName;
		var CurrentNo=req.body.CurrentNo;
		
		console.log('CompanyName:'+CompanyName+'CurrentNo:'+CurrentNo);
		io.sockets.emit("serverBroadcast",{no:CurrentNo,company:CompanyName,});
		
		res.end();
	}
	
}



