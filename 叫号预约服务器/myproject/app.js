
/**
 * Module dependencies.
 */

var express = require('express');
var routes = require('./routes');
var user = require('./routes/user');
var http = require('http');
var path = require('path');

var app = express();

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
};

app.get('/', routes.index);
app.get('/users', user.list);


app.post('/login',routes.login);//登陆
 
app.post('/adduser',routes.insert);//注册

//企业用户
app.post('/busin',routes.busin);//业务定制
app.post('/statis1',routes.statis1);//统计功能
app.post('/statis2',routes.statis2);
app.post('/getlist',routes.getlist);//获得已预约列表
app.post('/getname',routes.getname);
app.post('/num',routes.num);

								//人员定制
								//主业务功能

//一般用户
app.post('/seek',routes.seek);//搜索
app.post('/list',routes.list);//可预约列表

app.post('/getnum',routes.getnum);//获取号码
app.post('/reserve1',routes.reserve1);//大堂预约
app.post('/reserve2',routes.reserve2);//房间预约


var server=http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});

var io=require('socket.io').listen(server);

var MySocketManager=require("./routes/MySocketManager");
var SocketManager=new MySocketManager(io);
	
app.post('/Broadcast', SocketManager.boradcast);



