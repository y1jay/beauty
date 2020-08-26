const express = require("express");
const { createUser, loginUser } = require("../controller/user");
const router = express.Router();

router.route("/add").post(createUser);
router.route("/login").post(loginUser);

module.exports = router;
