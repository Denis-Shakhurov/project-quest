@import dto.StartPage
@param StartPage page

@template.layout.page(
content = @`
    <main class="flax-grow-1">
        <body >
        <section>
            <div class="container-fluid bg-light p-5">
                <div class="row">
                    <div class="col-md-10 col-lg-8 mx-auto text-dark">
                        <h1 class="display-3 mb-0">QuestGame</h1>
                        <p class="lead">${page.getTitle()}</p>
                        <form action="" method="post" class="rss-form text-body">
                            <div class="row">
                                <div class="col">
                                    <div class="form-floating">
                                        <input id="name-input" autofocus="" type="text" required="" name="name" aria-label="name"
                                               class="form-control w-80" placeholder="введи имя" autocomplete="off">
                                        <label for="name-input">Введи имя</label>
                                    </div>
                                </div>
                                <div class="col">
                                    <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-3">Выбрать игру</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        </body>
    </main>
`
)