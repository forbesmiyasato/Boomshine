// index.js

/**
 * Required External Modules
 */
let User = require('./Model/user');
const express = require("express");
const path = require("path");
const mongodb = require("mongodb");
const crypto = require("crypto");
var mongoose = require("mongoose");
var bodyParser = require('body-parser');

/**
 * App Variables
 */

const app = express();
const port = process.env.PORT || "8000";
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

/**
 *  App Configuration
 */
require('dotenv').config()

app.use(express.json());
mongoose.connect(`mongodb+srv://forbes:${process.env.DB_PW}@cluster0-lxt5l.mongodb.net/Boomshine?retryWrites=true&w=majority`, {
    useNewUrlParser: true,
    useCreateIndex: true,
    useUnifiedTopology: true
})

const connection = mongoose.connection;
connection.once('open', () => {
    console.log("MongoDB database connection established succesfully")
})
/**
 * Utility Functions
 */
var getRandomSalt = function (length) {
    return crypto.randomBytes(Math.ceil(length / 2))
        .toString('hex').slice(0);
}

var sha512 = function (password, salt) {
    var hash = crypto.createHmac('sha512', salt);
    hash.update(password);
    var value = hash.digest('hex');
    return {
        salt: salt,
        passwordHash: value
    }
}

function saltHashPassword(userPassword) {
    var salt = getRandomSalt(16);
    var password = sha512(userPassword, salt);

    return password;
}

function checkHashPassword(userPassword, salt) {
    var password = sha512(userPassword, salt)
    return password;
}
/**
 * Routes Definitions
 */

app.get("/", (req, res) => {
    res.status(200).send("Boomshine");
});


//Sign up
app.post('/register', (req, res, next) => {
    console.log("login body" + req.body.Password);
    var plainPassword = req.body.Password;
    var hashData = saltHashPassword(plainPassword);
    var HashedPassword = hashData.passwordHash;
    var Salt = hashData.salt;
    var Name = req.body.Name;
    var HighScore = 0;
    var Points = 0;
    var PWSuper = 0;
    var PWMulti = 0;
    var PWUltimate = 0;
    const NewUser = new User({
        Name,
        HashedPassword,
        Salt,
        HighScore,
        Points,
        PWSuper,
        PWMulti,
        PWUltimate
    });

    NewUser.save()
        .then(() => res.json('User Added'))
        .catch(() => res.json('Username already chosen'))
})

//Login
app.post('/login', (req, res, next) => {

    var Name = req.body.Name;
    var inputPassword = req.body.Password;
    User.findOne({ Name: Name }, function (err, user) {
        if (!user) {
            res.json('Login Fail');
            console.log('Login Fail');
            return;
        }
        var Salt = user.Salt;
        var HashedPassword = user.HashedPassword;
        var HashedInputPassword = checkHashPassword(inputPassword, Salt).passwordHash;
        if (HashedInputPassword == HashedPassword) {
            res.json('Login Success');
            console.log('login success');
        }
        else {
            res.json('Login Fail');
            console.log('Login Fail');
        }
    });
})

//Get user data
app.get('/GetUserData', (req, res, next) => {
    var Name = req.query.Name;
    User.findOne({ Name: Name }, function (err, user) {
        if (!user) {
            res.json('User not found');
            console.log('User not found');
            return;
        }
        console.log(user);
        res.json(user);
    });
})

//Update user data
app.post('/UpdateUserData', (req, res, next) => {
    var Name = req.body.Name
    var HighScore = req.body.HighScore;
    var Points = req.body.Points;
    var PWMulti = req.body.PWMulti;
    var PWSuper = req.body.PWSuper;
    var PWUltimate = req.body.PWUlti;
    var update = { HighScore: HighScore, Points: Points, PWMulti: PWMulti, PWSuper: PWSuper, PWUltimate: PWUltimate }

    console.log("Recevied Update");
    console.log(update)
    User.findOneAndUpdate({ Name: Name }, update, {
        new: true, useFindAndModify: false
    }, (err, doc) => {
        if (err) {
            console.log("Something wrong when updating data!");
        }

        console.log(doc);
    })
})

//Get highscores
app.get('/GetHighScore', (req, res, next) => {
    var Name = req.query.Name;
    User.find({}).select('Name HighScore -_id').sort({HighScore : 'descending'}).limit(5).then(response => {
        res.json(response);
    });
})

/**
 * Server Activation
 */

app.listen(port, () => {
    console.log(`Listening to requests on http://localhost:${port}`);
});