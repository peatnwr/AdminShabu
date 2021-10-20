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

//main function
app.post('/createTable', function(req, res) {
    let table = req.body.table;
    let amountCus = req.body.amountCus;
    let codeTable = req.body.codeTable;

    dbConn.query('SELECT * FROM `customer` WHERE cus_table = ?', table, function(error, results, fields){
        if(error) throw error;

        if(results && results.length){
            return res.status(400).send({ error: true, message: "This table have in database" })
        }else{
            dbConn.query('INSERT INTO `customer`(`cus_code`, `cus_amount`, `cus_table`) VALUES (?, ?, ?)', [codeTable, amountCus, table], function(error, results, fields){
                if(error) throw error;

                return res.send(results);
            });
        }
    });
});

app.get('/categoryFood', function(req, res) {
    dbConn.query('SELECT * FROM `foodcategory`', function(error, results, fields){
        if(error) throw error;

        return res.send(results);
    });
});

app.get('/food/:foodcategory_id', function(req, res) {
    let foodCategoryId = req.params.foodcategory_id

    if(!foodCategoryId){
        return res.status(400).send({ error: true, message: "Please provide food category id" });
    }else{
        dbConn.query('SELECT * FROM `food` WHERE foodcategory_id = ?', foodCategoryId, function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        });
    }
});

app.post('/insertFood/:foodcategory_id', function(req, res) {
    let foodCategoryId = req.params.foodcategory_id
    let foodName = req.body.food_name
    let foodImg = req.body.food_img
    let foodAmount = req.body.food_amount

    if(!foodCategoryId){
        return res.status(400).send({ error: true, message: "Please provide food category id" });
    }else{
        dbConn.query('INSERT INTO `food`(`food_name`, `food_img`, `food_amount`, `foodcategory_id`) VALUES (?, ?, ?, ?)', [foodName, foodImg, foodAmount, foodCategoryId], function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        });
    }
});

app.put('/editFood/:food_id', function(req, res) {
    let foodId = req.params.food_id
    let foodName = req.body.food_name
    let foodAmount = req.body.food_amount
    let foodImage = req.body.food_img

    if(!foodId){
        return res.status(400).send({ error: true, message: "Please provide food id" });
    }else{
        dbConn.query('UPDATE `food` SET `food_name` = ?, `food_img` = ?, `food_amount` = ? WHERE `food_id` = ?', [foodName, foodImage, foodAmount, foodId], function(error, results, fields){
            if(error) throw error;
            
            return res.send(results);
        });
    }
});

app.delete('/deleteFood/:food_id', function(req, res) {
    let foodId = req.params.food_id

    if(!foodId){
        return res.status(400).send({ error: true, message: "Please provide food id" });
    }else{
        dbConn.query('DELETE FROM `food` WHERE `food_id` = ?', foodId, function(error, results, fields){
            if(error) throw error;

            return res.send(results);
        })
    }
});

//authentication function
app.post('/loginEmp', function(req, res) {
    let username = req.body.username;
    let password = req.body.password;

    dbConn.query('SELECT * FROM `employee` WHERE emp_username = ?', username, function(error, results, fields){
        if(error) throw error;

        if(results && results.length){
            let passwordFromDb = results[0].emp_password
            if(password == passwordFromDb){
                return res.send(results[0]);
            }else{
                return res.status(400).send({ error: true, message: "Password does not match" });
            }
        }else{
            return res.status(400).send({ error: true, message: "User does not exist" })
        }
    });
    
});

app.listen(3000, function(){
    console.log('Node api is running on port 3000');
});

module.exports = app;