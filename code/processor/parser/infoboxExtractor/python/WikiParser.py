""" 
Parser code to extract only infoboxes

Extract your wiki dump files and remove the numerical 
part in the trailing end of the filename 

Required filenames: enwiki-2013*.xml

Modify infobox_type list for different infoboxes

"""

from glob import glob
import traceback
import sys

search_text = "{{Infobox"
infobox_type=['person','settlement','film']
total_number_of_infoboxes=0
for each in infobox_type:
    string = ""
    number_of_infoboxes = 0
    key = []
    mystack=[]
    try:
        fnames = glob("enwiki-2013*.xml")
        if not fnames:
            print "Sorry! Unable to find Wikipedia Dump XML files \nin the current path!"
            print "\nCopy this file to the folder where your \nenwiki-2013* files are present and then run"
            sys.exit(0)
        #print fnames
        for fname in fnames:
            file_number_of_infoboxes = 0
            with open(fname) as f:
                print "Processing "+fname+".........."
                for line in f:
                    if search_text+" "+each in line:
                        file_number_of_infoboxes+=1
                        next_line=line
                        while True:
                            #mystack.append("{{")
                            string+=next_line
                            #print next_line
                            #print mystack
                            if next_line.startswith("|") and "=" in next_line:
                                key.append(next_line.split("=")[0].replace("|","").strip())
                            #while not next_line.startswith("}}"):
                            ##print "OPen------>", next_line.count("{{")
                            #print "close-------->", next_line.count("}}")

                            if "{{" in next_line:
                                mystack.extend(["{{"]*next_line.count("{{"))
                            #print mystack
                            #print "close-------->", next_line.count("}}")
                            for i in range(next_line.count("}}")):
                                try:
                                    mystack.pop()
                                except:
                                    break

                            if not mystack:
                                break
                            next_line = f.next()

                        else:
                            string+="}}\n"
            print "Number of infoboxes in "+fname+" is "+str(file_number_of_infoboxes)
            number_of_infoboxes+=file_number_of_infoboxes
    except (SystemExit, KeyboardInterrupt):
        sys.exit(0)    
    except:
        print "Oh! uh!"
        print traceback.format_exc()

    wr = open("infobox_"+each+".txt","w")
    wr.write(string)
    wr.close()
    #print "******************************Finally******************************"
    #print string
    #print set(key)
    print "Number of unique keys in Infobox "+each+" is "+str(len(set(key)))
    print "Number of infoboxes in "+each+" is "+str(number_of_infoboxes)
    total_number_of_infoboxes+=number_of_infoboxes

print "Total Number of infoboxes is "+str(total_number_of_infoboxes)
