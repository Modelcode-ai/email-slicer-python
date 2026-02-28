print("Please enter your Email Id:")
email = input().strip()

if email.find("@") != -1:
    username = email[:email.index("@")]     # username value = from 0 to index < @
    domain = email[email.index("@") + 1:]   # domain value = from index > @ to end
    print("Your username is: ", username)
    print("Your domain is: ", domain)
else:
    print("Please enter a valid Email Id.")

# if email contains @ then valid email id otherwise invalid email id
