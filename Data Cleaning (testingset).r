###############CA684 Machine Learning Project###########
###############Tweets sentiment analysis################
#####################Data Cleaning######################


#Note:
#1. In both given testing set and test set, each word has a label, e.g."great==A, Galway==^"
#2. This script will only keep the suitable words for this analysis after classify the labels. 
#3. This script 
#End

######################1 Preparation#####################
#For Mac, set the path of data file.
setwd('/Users/xuanyang/Documents/CA684 Machine Learning/SentimentAnalysis')
#Import the testing set.
tw_test <- read.csv('./TweetsData/test.csv')
#The main aim is normalise the first column in tw_test, so set it to a new dataset.
tw_test_1 <- tw_test$Irish..A.premier..N.Cowen....survives..V.vote..V......Irish..A.PM..N.Brian....Cowen....survives..V.a..D.secret..N.confidence..N.vote..V.on..P.his..D.leadershi..N......URL.Link..V.
#Get all labels in testing set.
gsub(".*==|==.*", "", tw_test_1)

#Partly results:
#[1] "V " "# " "# " "E " ", " "A " "A " "N " ", " "R " "^ " "N " "N "
#[14] "^ " ", " ", " ", " ", " "N " ", " ", " "V " ", " ", " ", " "V "
#[27] "^ " ", " ", " ", " "# " ", " "V " "# " "N " "V " ", " ", " ", "
#[40] ", " ", " ", " "N " "E " "N " "^ " ", " "P " "! " ", " ", " "N "
#[53] "N " "V " "^ " ", " "# " "^ " ", " "N " "E " "# " "O " "# " "# "

#Compare to the contents of testing set, conclude:
#1. "V": Verb, keep.
#2. "#": Tags of topic, delete.
#3. "E": Emotion, keep.
#4. ",": Punctuation mark, delete.
#5. "A": Adjective, keep.
#6. "N": Noun, delete
#7. "R": Adjective, keep.
#8. "^": Special name, delete.
#9. "P": Preposition, delete.
#10. "!": Abbreviation of emotion, keep.
#11. "O": Objective, delete.
#12. "@": ID, delete.
#13. "G": Symbol or abbreviation, delete.
#14. "$": Number, delete.
#15. "S": Abbreviation of noun, delete.
#16. "D": Art, delete.
#17. "L": Objective, delete.
#18. "&": And, delete.
#19. "U": You, delete.
#20. "T": Adverb, delete.
#21. "X": Adverb, delete.
#22. "Z": Abbreviation of noun, delete.
####################End 1######################

################2 Keep&Delete labels#################

#Keep:
#"V", "E", "A", "R", "!"

tw_test_r <- gsub("==V", "", tw_test_1)
tw_test_r <- gsub("==E", "", tw_test_r)
tw_test_r <- gsub("==A", "", tw_test_r)
tw_test_r <- gsub("==R", "", tw_test_r)
tw_test_r <- gsub("==!", "", tw_test_r)
tw_test_r 

#Delete:
#",", "#", "N", "^", "P", "O", "@", "G", "$", "S", "D", "L", "&", "U", "T", "Z"

tw_test_r <- gsub("\\S*==, ", "", tw_test_r)
tw_test_r <- gsub("\\S*==# ", "", tw_test_r)
tw_test_r <- gsub("\\S*==N ", "", tw_test_r)
tw_test_r <- gsub("\\S*==\\^ ", "", tw_test_r)
tw_test_r <- gsub("\\S*==P ", "", tw_test_r)
tw_test_r <- gsub("\\S*==O ", "", tw_test_r)
tw_test_r <- gsub("\\S*==@ ", "", tw_test_r)
tw_test_r <- gsub("\\S*==G ", "", tw_test_r)
tw_test_r <- gsub("\\S*==\\$ ", "", tw_test_r)
tw_test_r <- gsub("\\S*==S ", "", tw_test_r)
tw_test_r <- gsub("\\S*==D ", "", tw_test_r)
tw_test_r <- gsub("\\S*==L ", "", tw_test_r)
tw_test_r <- gsub("\\S*==& ", "", tw_test_r)
tw_test_r <- gsub("\\S*==U ", "", tw_test_r)
tw_test_r <- gsub("\\S*==T ", "", tw_test_r)
tw_test_r <- gsub("\\S*==X ", "", tw_test_r)
tw_test_r <- gsub("\\S*==Z ", "", tw_test_r)
tw_test_r
####################End 2#####################

##################3 Normalise###################

#Delete "URL-Link"
tw_test_r <- gsub("URL-Link", "", tw_test_r)
#Delete rest punctuations
tw_test_r <- gsub("[[:punct:]]", "", tw_test_r)
#Delete rest numbers
tw_test_r <- gsub("[[:digit:]]", "", tw_test_r)
#Convert uppercase to lowercase
tw_test_r <- tolower(tw_test_r)
#Replace prepared dataset to the original one
tw_test$Irish..A.premier..N.Cowen....survives..V.vote..V......Irish..A.PM..N.Brian....Cowen....survives..V.a..D.secret..N.confidence..N.vote..V.on..P.his..D.leadershi..N......URL.Link..V. <- tw_test_r
#Delete last useless column
tw_test$Brian.Cowen <- NULL
#Save
write.table(tw_test, file = "./TweetsData/pre_test.csv", row.names = F, col.names = F, sep = ",")
##################End 3##########################