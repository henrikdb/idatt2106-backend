def modify_html(file_path):
    import bs4

    # Read the original HTML content from a file
    with open(file_path, 'r', encoding='utf-8') as file:
        original_html = file.read()

    # Parse the original HTML content using Beautiful Soup
    soup = bs4.BeautifulSoup(original_html, features='html.parser')
    
    # Find the <ul> element that contains the Checkstyle link inside the Project Reports navigation section
    nav_list = soup.find("li", class_="active").find("ul", class_="nav nav-list")
    
    # Create the new list item for JaCoCo
    new_li = soup.new_tag("li")
    new_a = soup.new_tag("a", href="jacoco/index.html")
    new_a.string = "Jacoco Coverage"
    new_li.append(new_a)
    
    # Add the new list item to the navigation list
    nav_list.append(new_li)
    
    # Write the modified HTML content back to the original file
    with open(file_path, 'w', encoding='utf-8') as file:
        file.write(str(soup.prettify()))

# Specify the path to your HTML file
file_path = 'target/site/project-reports.html'
modify_html(file_path)
