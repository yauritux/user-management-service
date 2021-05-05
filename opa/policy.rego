package authz

#import data.acl
#import input 

default allow = false

allow {
  input.method == "POST"
  input.path = ["api", "v1", "users"]
  is_admin
  #token.payload.role == "ROLE_ADMIN" 
}

allow {
  some username
  input.method == "PUT"
  input.path = ["api", "v1", "users", username]
  is_user
  input.name == username
}

is_admin {
  some i
  input.roles[i].authority == "ROLE_ADMIN"
}

is_user {
  some i
  input.roles[i].authority == "ROLE_USER"
}

# Ensure that the token was issued by the user
#user_owns_token { 
#  input.user == token.payload.username
#}

# Helper to get the token payload.
token = {"payload": payload} {
  [header, payload, signature] := io.jwt.decode(input.token)
}
