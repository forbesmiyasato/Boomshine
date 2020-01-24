const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const UserSchema = new Schema(
    {
        Name: String,
        HashedPassword: String,
        Salt : String,
        HighScore: Number,
        Points: Number,
        PWSuper: Number,
        PWMulti: Number,
        PWUltimate: Number
    }
);

// export the new Schema so we could modify it using Node.js
module.exports = mongoose.model("User", UserSchema);