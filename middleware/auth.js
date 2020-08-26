const jwt = require("jsonwebtoken");
const connection = require("../myconnection");

const auth = async (req, res, next) => {
  let token;
  try {
    token = req.header("Authorization").replace("Bearer ", ""); // 베어러 제거
  } catch (e) {
    res.status(411).json({ error: "Please authenticate!" });
    return;
  }
  const decoded = jwt.verify(token, process.env.ACCESS_TOKEN_SECRET);
  let user_id = decoded.user_id;
  let query = `slect * from beauty_user where id= ${user_id}`;
  try {
    [rows] = await connetion.query(query);
    if (rows.length == 0) {
      res.status(401).json({ error: "Please authenticate!" });
    } else {
      let user = rows[0];
      //패스워드 정보는 필요없으니 삭제하고 담아줄것
      delete user.passwd;
      req.user = user;
      req.user.token = token;
      next();
    }
  } catch (e) {
    res.status(401).json({ success: false, error: e });
  }
};

module.exports = auth;
