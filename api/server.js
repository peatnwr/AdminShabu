let express = require('express');
let app = express();
let bodyParser = require('body-parser');
let mysql = require('mysql');

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({
    extended: true
}));

let dbConn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'shabu'
});

dbConn.connect();

//authentication function
app.post('/loginEmp', function(req, res) {
    let username = req.body.username;
    let password = req.body.password;

    if(!username && !password){
        return res.status(400).send({ error: true, message: "Please provide username and password" });
    }else if(!username || !password){
        return res.status(400).send({ error: true, message: "Please provide username or password" });
    }else{
        dbConn.query('SELECT * FROM `employee` WHERE Emp_username = ?', username, function(error, results, fields){
            if(error) throw error;

            if(results && results.length){
                let passwordFromDb = results[0].emp_password
                if(password == passwordFromDb){
                    dbConn.query('SELECT * FROM `employee` WHERE Emp_username = ?', username, function(error, results, fields){
                        if(error) throw error;

                        return res.send(results);
                    });
                }else{
                    return res.status(400).send({ error: true, message: "Password does not match" });
                }
            }else{
                return res.status(400).send({ error: true, message: "User does not exist" })
            }
        });
    }
});

app.listen(3000, function(){
    console.log('Node api is running on port 3000');
});

module.exports = app;